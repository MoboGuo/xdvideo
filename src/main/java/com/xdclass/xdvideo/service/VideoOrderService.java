package com.xdclass.xdvideo.service;

import com.xdclass.xdvideo.domain.VideoOrder;
import com.xdclass.xdvideo.dto.VideoOrderDto;

/**
 * @author 10782
 * @date 2020-04-04 17:22
 **/
public interface VideoOrderService {

    /**
     * 下单接口
     * @param videoOrderDto
     * @return
     */
    String save(VideoOrderDto videoOrderDto) throws Exception;

    /**
     * 根据流水号查找订单
     * @param outTradeNo
     * @return
     */
    VideoOrder findByOutTradeNo(String outTradeNo);

    /**
     * 根据流水号更新订单
     * @param videoOrder
     * @return
     */
    int updateVideoOderByOutTradeNo(VideoOrder videoOrder);
}
