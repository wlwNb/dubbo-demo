package com.wlw.common.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;

/**
 * @Author: wlw
 * @Date: 2021/03/04 13:59
 */
@Activate(group = Constants.PROVIDER, order = 2)
@Slf4j
public class CustomProviderLogFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String traceId = RpcContext.getContext().getAttachments().get(CustomConsumerSignFilter.TRACEID);
        String sign = RpcContext.getContext().getAttachments().get(CustomConsumerSignFilter.SIGN);
        String signNew = DigestUtils.md5DigestAsHex(invoker.getUrl().getServiceKey().getBytes());
        String serviceKey = invoker.getUrl().getServiceKey();
        Object[] args = invocation.getArguments();
        String prefix = "[traceId:" + traceId + "] DUBBO调用日志:[" + serviceKey + "]";
        if (sign == null || !sign.equals(signNew)) {
			log.error(prefix + " 校验sign失败");
			throw new RpcException("校验sign失败");
		}
        log.info(prefix + " 入参=>" + JSON.toJSONString(args));
        long start = System.currentTimeMillis();
        Result r = invoker.invoke(invocation);
        if (r.hasException()) {
            Throwable e = r.getException();
            if (e.getClass().getName().equals("java.lang.RuntimeException")) {
                log.error(prefix + " 运行时异常=>" + JSON.toJSONString(r));
            } else {
                log.error(prefix + " 异常=>" + JSON.toJSONString(r));
            }
        } else {
            log.info(prefix + " 返参=>" + JSON.toJSONString(r.getValue()));
            log.info(prefix + " 调用耗时=>" + (System.currentTimeMillis() - start) + "ms");
        }
        return r;

    }

}