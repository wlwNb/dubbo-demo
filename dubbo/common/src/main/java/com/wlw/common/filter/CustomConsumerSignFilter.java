package com.wlw.common.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.wlw.common.utils.twitter.SnowflakeIdUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;

/**
 * @Author: wlw
 * @Date: 2021/03/05 10:26
 */
@Slf4j
@Activate(group = Constants.CONSUMER, order = -10000)
public class CustomConsumerSignFilter implements Filter {

	public static final String SIGN = "sign";
	public static final String TRACEID = "traceId";

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		String sign = DigestUtils.md5DigestAsHex(invoker.getUrl().getServiceKey().getBytes());
		String traceId = SnowflakeIdUtils.nextId();
		RpcContext.getContext().setAttachment(SIGN, sign);
		RpcContext.getContext().setAttachment(TRACEID, traceId);
		log.info("DUBBO消费端调用提供方host:{},port:{},API:{},参数透传:[traceId:{},sign:{}]",
				invoker.getUrl().getHost(), invoker.getUrl().getPort(),
				invoker.getUrl().getServiceKey(), traceId, sign);
		return invoker.invoke(invocation);
	}

}