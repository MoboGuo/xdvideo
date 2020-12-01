package com.xdclass.xdvideo.controller;

import com.xdclass.xdvideo.config.WeChatConfig;
import com.xdclass.xdvideo.domain.JsonData;
import com.xdclass.xdvideo.domain.User;
import com.xdclass.xdvideo.domain.VideoOrder;
import com.xdclass.xdvideo.service.UserService;
import com.xdclass.xdvideo.service.VideoOrderService;
import com.xdclass.xdvideo.service.VideoService;
import com.xdclass.xdvideo.utils.JwtUtils;
import com.xdclass.xdvideo.utils.WXPayUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;

/**
 * @author 10782
 * @date 2020-03-31 11:21
 **/
@RestController
@RequestMapping("api/v1/wechat")
public class WeChatController {

    @Resource
    private WeChatConfig weChatConfig;

    @Resource
    private UserService userService;

    @Resource
    private VideoOrderService videoOrderService;

    @GetMapping("login_url")
    public JsonData loginUrl(@RequestParam(value = "access_page", required = true) String accessPage) throws UnsupportedEncodingException {

        String redirectUrl = weChatConfig.getOpenRedirectUrl();//获取开放平台重定向地址

        String callbackUrl = URLEncoder.encode(redirectUrl,"UTF-8");//进行编码

        String qrcodeUrl = String.format(weChatConfig.getOpenQrcodeUrl(),weChatConfig.getOpenAppid(),callbackUrl,accessPage);

        return JsonData.buildSuccess(qrcodeUrl);
    }

    /**
     * 微信扫码登陆，回调地址
     * @param code
     * @param state
     * @param response
     */
    @GetMapping("/user/callback")
    public void wechatUserCallback(@RequestParam(value = "code", required = true) String code,
                                   String state, HttpServletResponse response) throws IOException {
//        System.out.println(code);
//        System.out.println(state);
        User user = userService.saveWeChatUser(code);
        if(user != null){
            //生成JWT
            String token = JwtUtils.geneJsonWebToken(user);
            //status 当前用户的页面地址，需要拼接http://才不会站内跳转
            response.sendRedirect(state+"?token="+ token + "&head_img=" + user.getHeadImg() + "&name=" + URLEncoder.encode(user.getName(),"UTF-8")+"&user_id="+user.getId());
        }
    }

    /**
     * 微信支付回调
     * @param request
     * @param response
     */
    @RequestMapping("/order/callback")
    public void orderCallBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
        InputStream inputStream = request.getInputStream();

        //BufferedReader是包装设计模式，性能更高
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = in.readLine()) != null){
            sb.append(line);
        }
        in.close();;
        inputStream.close();
        Map<String, String> callbackMap = WXPayUtil.xmlToMap(sb.toString());
        System.out.println(callbackMap);

        SortedMap<String, String> sortedMap = WXPayUtil.getSortedMap(callbackMap);

        //判断签名是否正确
        if(WXPayUtil.isCorrectSign(sortedMap, weChatConfig.getKey())){
            String outTradeNo = sortedMap.get("out_trade_no");
            VideoOrder dbVideoOrder = videoOrderService.findByOutTradeNo(outTradeNo);
            sortedMap.get("total_fee");
            dbVideoOrder.getTotalFee();
            if(dbVideoOrder != null && dbVideoOrder.getState()==0){//业务逻辑判断
                VideoOrder videoOrder = new VideoOrder();
                videoOrder.setOpenid(sortedMap.get("openid"));
                videoOrder.setOutTradeNo(outTradeNo);
                videoOrder.setNotifyTime(new Date());
                videoOrder.setState(1);
                int row = videoOrderService.updateVideoOderByOutTradeNo(videoOrder);
                if(row == 1){//通知微信订单处理成功
                    response.setContentType("text/xml");
                    response.getWriter().println("success");
                }
            }
        }
        response.setContentType("text/xml");
        response.getWriter().println("fail");
    }
}
