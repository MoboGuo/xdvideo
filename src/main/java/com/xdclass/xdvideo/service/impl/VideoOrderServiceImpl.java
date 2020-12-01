package com.xdclass.xdvideo.service.impl;

import com.xdclass.xdvideo.config.WeChatConfig;
import com.xdclass.xdvideo.domain.User;
import com.xdclass.xdvideo.domain.Video;
import com.xdclass.xdvideo.domain.VideoOrder;
import com.xdclass.xdvideo.dto.VideoOrderDto;
import com.xdclass.xdvideo.exception.XdException;
import com.xdclass.xdvideo.mapper.UserMapper;
import com.xdclass.xdvideo.mapper.VideoMapper;
import com.xdclass.xdvideo.mapper.VideoOrderMapper;
import com.xdclass.xdvideo.service.VideoOrderService;
import com.xdclass.xdvideo.utils.CommonUtils;
import com.xdclass.xdvideo.utils.HttpUtils;
import com.xdclass.xdvideo.utils.WXPayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author 10782
 * @date 2020-04-04 17:25
 **/
@Service
public class VideoOrderServiceImpl implements VideoOrderService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Logger dataLogger = LoggerFactory.getLogger("dataLogger");

    @Resource
    private WeChatConfig weChatConfig;

    @Resource
    private VideoMapper videoMapper;

    @Resource
    private VideoOrderMapper videoOrderMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String save(VideoOrderDto videoOrderDto) throws Exception {
        dataLogger.info("module=video_order`api=save`user_id={}`video_id={}",videoOrderDto.getUserId(),videoOrderDto.getVideoId());
        //查找视频信息
        Video video =  videoMapper.findById(videoOrderDto.getVideoId());

        //查找用户信息
        User user = userMapper.findById(videoOrderDto.getUserId());

        //生成订单
        VideoOrder videoOrder = new VideoOrder();
        videoOrder.setTotalFee(video.getPrice());
        videoOrder.setVideoImg(video.getCoverImg());
        videoOrder.setVideoTitle(video.getTitle());
        videoOrder.setCreateTime(new Date());
        videoOrder.setVideoId(video.getId());
        videoOrder.setState(0);
        videoOrder.setUserId(user.getId());
        videoOrder.setHeadImg(user.getHeadImg());
        videoOrder.setNickname(user.getName());

        videoOrder.setDel(0);
        videoOrder.setIp(videoOrderDto.getIp());
        videoOrder.setOutTradeNo(CommonUtils.generateUUID());

        videoOrderMapper.insert(videoOrder);

        //获取codeurl
        String codeurl = unifiedOrder(videoOrder);


        return codeurl;
    }


    /**
     * 同意下单方法
     * @param videoOrder
     * @return
     */
    private String unifiedOrder(VideoOrder videoOrder) throws Exception {
//        if(true){
//            throw new XdException(0,"测试错误自定义");
//        }
        //生成签名
        SortedMap<String,String> params = new TreeMap<>();
        params.put("appid",weChatConfig.getAppId());
        params.put("mch_id", weChatConfig.getMchId());
        params.put("nonce_str",CommonUtils.generateUUID());
        params.put("body",videoOrder.getVideoTitle());
        params.put("out_trade_no",videoOrder.getOutTradeNo());
        params.put("total_fee",videoOrder.getTotalFee().toString());
        params.put("spbill_create_ip",videoOrder.getIp());
        params.put("notify_url",weChatConfig.getPayCallbackUrl());
        params.put("trade_type","NATIVE");

        //sign签名
        String sign = WXPayUtil.createSign(params, weChatConfig.getKey());
        params.put("sign",sign);

        //map转xml
        String payXml = WXPayUtil.mapToXml(params);

        //统一下单
        String orderStr = HttpUtils.doPost(WeChatConfig.getUnifiedOrderUrl(), payXml, 10000);
        if(orderStr == null){
            return null;
        }
        Map<String, String> unifiedOrderMap = WXPayUtil.xmlToMap(orderStr);
        System.out.println(unifiedOrderMap.toString());
        if(unifiedOrderMap != null){
            return unifiedOrderMap.get("code_url");
        }
        return "";
    }

    @Override
    public VideoOrder findByOutTradeNo(String outTradeNo) {
        return videoOrderMapper.findByOutTradeNo(outTradeNo);
    }

    @Override
    public int updateVideoOderByOutTradeNo(VideoOrder videoOrder) {
        return videoOrderMapper.updateVideoOrderByOutTradeNo(videoOrder);
    }
}
