package com.meteor.curator.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.meteor.curator.core.constants.ZKConstant;
import com.meteor.curator.core.exception.ZKDataNullException;
import com.meteor.curator.core.utils.ConvertUtil;
import com.meteor.curator.core.utils.FileDefineUtil;
import com.meteor.curator.core.pojo.ZKNode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.curator.CuratorConnectionLossException;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.GetChildrenBuilder;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ZKUtil {
    private static final Logger logger = LoggerFactory.getLogger(ZKUtil.class);


    public static ZKNode tree(String path) throws Exception {
        ZKNode root = null;
        if (StringUtils.isEmpty(path)) {
            path = ZKConstant.ROOT_PATH;
        }
        CuratorFramework client = Initializer.getCuratorFramework();
        GetChildrenBuilder children = client.getChildren();
        root = new ZKNode(path, path);
        buildTree(children, root);
        return root;
    }

    /**
     * @param children 子级
     * @param node     zK节点
     * @throws Exception
     */
    private static void buildTree(GetChildrenBuilder children, ZKNode node) throws Exception {
        String fullPath = node.getFullPath();
        List<String> child_path_list = children.forPath(fullPath);
        if (CollectionUtils.isEmpty(child_path_list)) {
            node.setData(ZKUtil.getData(fullPath));

            return;
        }

        List<ZKNode> childs = node.getChilds();
        if (CollectionUtils.isEmpty(childs)) {
            childs = new ArrayList<ZKNode>();
            node.setChilds(childs);
        }

        for (String child_path : child_path_list) {
            ZKNode children_node = null;
            if (StringUtils.equalsIgnoreCase(fullPath, ZKConstant.ROOT_PATH)) {
                children_node = new ZKNode(child_path, fullPath + child_path);
                buildTree(children, children_node);
            } else {
                children_node = new ZKNode(child_path, fullPath + "/" + child_path);
                buildTree(children, children_node);
            }
            childs.add(children_node);
        }
    }


    public static List<String> getChilds(String path) throws Exception {
        path = checkPath(path,false);
        CuratorFramework client = Initializer.getCuratorFramework();
        GetChildrenBuilder children = client.getChildren();
        List<String> childs = children.forPath(path);
        return childs;
    }

    /**
     * 路径调整
     *
     * @param path 路径
     * @return
     */
    public static String checkPath(String path, boolean add_cloud) {
        if (!StringUtils.startsWithIgnoreCase(path, "/") || !StringUtils.contains(path, "/")) {
            if (!StringUtils.startsWithIgnoreCase(path, "/")) {
                path = "/" + path;
            }
            path = path.trim().replace(".", "/");
        }

        if (!add_cloud) {
            return path;
        }
        if (!StringUtils.startsWithIgnoreCase(path, ZKConstant.ROOT_PATH)) {
            path = ZKConstant.ROOT_PATH + path;
        }
        return path;
    }


    public static void setData(String path, String data) throws Exception {
        if (StringUtils.isEmpty(data)) {
            ZKUtil.setData(path, new byte[]{});
            return;
        }
        // 需要进行抓转码
        ZKUtil.setData(path, data.getBytes("UTF-8"));
    }

    public static void setData(String path, byte[] data) throws Exception {
        if (StringUtils.equals(path, "/")) {
            return;
        }
        path = checkPath(path,true);

        CuratorFramework client = Initializer.getCuratorFramework();
        Stat stat = client.checkExists().forPath(path);
        if (null == stat) {
            client.create().creatingParentsIfNeeded().forPath(path, data);
        } else {
            client.setData().forPath(path, data);
        }
        //写入磁盘
        FileDefineUtil.write(path, new String(data).getBytes());
        //写入内存
        ZKUtil.MEM_CACHE_MAP.put(path, new String(data));
    }

    public static void remove(String path) throws Exception {
        remove(path, true);
    }

    public static void remove(String path, boolean deleteChilds) throws Exception {
        if (StringUtils.equals(path, "/")) {
            return;
        }
        path = checkPath(path,false);

        CuratorFramework client = Initializer.getCuratorFramework();
        if (deleteChilds) {
            client.delete().deletingChildrenIfNeeded().forPath(path);
        } else {
            client.delete().forPath(path);
        }

        FileDefineUtil.remove(path);
        ZKUtil.MEM_CACHE_MAP.remove(path);
    }

    public static String getData(String path) throws Exception {
        if (StringUtils.equals(path, "/")) {
            return null;
        }
        // 判断内存是否有 该文件
        String result = MEM_CACHE_MAP.get(path);
        if (null != result) {
            return result;
        }
        // 判断本地磁盘是否有该文件
        result = FileDefineUtil.read(path);
        if (null != result) {
            return result;
        }

        CuratorFramework client = Initializer.getCuratorFramework();
        byte[] data = client.getData().forPath(path);
        if (null == data) {
            throw new ZKDataNullException("zk can not getData forPath " + path);
        }
        result = new String(data, "UTF-8");

        // 写入磁盘文件
        FileDefineUtil.write(path, result.getBytes());
        // 写入内存
        MEM_CACHE_MAP.put(path, result);

        return StringUtils.trim(result);
    }

    private static final Map<String, String> MEM_CACHE_MAP = new ConcurrentHashMap<String, String>();


    /**
     * 删除配置信息
     *
     * @param key 路径path
     */
    public static void removeItem(String key) {
        removeMem(key);
        FileDefineUtil.remove(key);
    }

    public static void removeMem(String key) {
        ZKUtil.MEM_CACHE_MAP.remove(key);
    }

    public static void writeItem(String path, String value) {
        ZKUtil.MEM_CACHE_MAP.put(path, value);
        FileDefineUtil.write(path, value.getBytes());
    }


    /**
     * 获得节点数据并进行转换 支持 基本类型 ，Map,Properties,list ,
     * 默认使用FastJSON转化对象
     *
     * @param path  key
     * @param clazz 类型
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T getData(String path, Class<T> clazz) throws Exception {
        return ConvertUtil.convert(getData(path), clazz);
    }

    /**
     * 切换服务前先测试下是否能连
     *
     * @param server
     */
    public static boolean testConnection(String server) {
        boolean flag = false;
        // 这是一个重试策略
        RetryPolicy retryPolicy = new RetryNTimes(0, 0);
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(server, retryPolicy);
        curatorFramework.start();
        try {
            // 检测连接是否成功
            curatorFramework.getZookeeperClient().getZooKeeper();
            // 官方没有提供相应的连接状态只能测试获得实例。
            curatorFramework.getData().forPath("/");
            flag = true;
        } catch (CuratorConnectionLossException connectionException) {

        } catch (Exception e) {
            logger.error("错误地址！{}", server);
        } finally {
            curatorFramework.close();
        }
        return flag;
    }

}
