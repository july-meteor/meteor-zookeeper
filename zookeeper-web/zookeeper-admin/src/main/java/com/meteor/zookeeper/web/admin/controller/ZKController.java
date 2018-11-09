package com.meteor.zookeeper.web.admin.controller;

import com.meteor.zookeeper.Initializer;
import com.meteor.zookeeper.ZKUtil;
import com.meteor.zookeeper.config.CuratorConfig;
import com.meteor.zookeeper.pojo.ZKNode;
import com.meteor.zookeeper.web.admin.service.ZKService;
import com.meteor.zookeeper.web.api.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * created by Meteor on 2018/10/27
 */
@RestController
@RequestMapping("/zk")
public class ZKController {
    private static final Logger log = LoggerFactory.getLogger(ZKController.class);

//    private static final String MENU_URL = "/module/admin/urls";

    @Autowired
    private ZKService zkService;

//    @GetMapping("/otherMenu")
//    public Response<?> otherMenu(){
//        Response<List<MenuVo>> resp = new Response<List<MenuVo> >();
//        List<MenuVo> result = new ArrayList<MenuVo>();
//        try {
//            Map<String,String> menuMap = ZKUtil.getData(MENU_URL,Map.class);
//
//            for (Map.Entry<String, String> entry : menuMap.entrySet()) {
//                MenuVo menuVo = new MenuVo();
//                menuVo.setName(entry.getKey());
//                String urlStr = entry.getValue();
//                //进行字符拆分
//                if (urlStr.contains("%")){
//                    String[] strAry = urlStr.split("%");
//                    urlStr = strAry[0];
//                    menuVo.setOpen(true);
//                }
//                menuVo.setUrl(urlStr);
//                result.add(menuVo);
//            }
//            resp.setData(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return  resp;
//    }

//    @GetMapping("myHtml")
//    public Response<String> getOtherHtml(@RequestParam( value = "url" ,required = true) String url){
//        Response<String> result = new Response<>();
//        String myHtml = HttpUtil.get(url,"UTF-8");
//        result.setData(myHtml);
//        return  result;
//    }

    //获取当前服务下的zNode
    @GetMapping(value = "/tree")
    public Result<?> tree(String path) {
        Map<String, Object> result = null;
        try {
//            //每次获取tree都先清除本地的数据，来保证数据是最新的。因为
//            FileDefineUtil.remove(null);
            result = zkService.getTreeByCurServer(path);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return new Result(result);
    }

    @PostMapping("/setData")
    public Result<?> setData(String path, String data) throws Exception {
        ZKUtil.setData(path, data);
        return new Result();
    }

    @DeleteMapping(value = "/remove")
    public Result<?> remove(String path) throws Exception {
        ZKUtil.remove(path);
        return new Result();
    }

    @GetMapping("/getData")
    public Result<?> getData(String path) throws Exception {
        return new Result(ZKUtil.getData(path));
    }


    @PostMapping("/servers")
    public Result<?> setServer(@RequestParam(value = "server", required = true) String server) {
        Result<String> resp = new Result<String>();
        try {
            if (ZKUtil.testConnection(server)) {
                log.info("切换Zookeeper服务 旧地址：{}  ，新地址：{}", CuratorConfig.ZK_SERVERS, server);
                CuratorConfig.ZK_SERVERS = server;
                Initializer.reInit();//手动重启
                resp.setRel(true);
            } else {
                resp.setRel(false);
            }
        } catch (Exception e) {//会出现一个连接异常，不需要处理

        }
        return resp;
    }

//
    @PostMapping("login")
    @ResponseBody
    public Result<?> login(@RequestParam String account, @RequestParam String password) throws Exception {
        Result<Object> result = new Result<>();
//        String zkAccount = ZKUtil.getData(CommonConstan.ACCOUNT_PATH),
//                paw = ZKUtil.getData(CommonConstan.PASSWORD_PATH);
//        if (account.equals(zkAccount) && (password.equals(paw) || paw == null)) {
//            //下发token
//            result.setData("Meteor");
//        } else {
//            result.setStatus(403);
//        }
        result.setData("Meteor");
        return result;
    }

    /**
     * 导入指定的文件
     *
     * @param zkNode
     * @return
     * @throws Exception
     */
    @PostMapping("import")
    public Result<?> importZNode(@RequestBody ZKNode zkNode) throws Exception {
        zkService.importZKNode(zkNode);
        return new Result();
    }
}
