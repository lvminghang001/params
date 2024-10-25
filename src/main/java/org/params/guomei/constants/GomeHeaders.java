package org.params.guomei.constants;

/**
 * @author zhangfuqiang3@gome.com.cn
 * @version 1.0
 * @date 2023/12/28
 * @description: 国美传输给流量方平台的header
 **/
public interface GomeHeaders {

    String RANDOM_KEY = "randomKey"; // decrypt data use it decode plain text from orgin ciphertext.

    String SIGN_DATA = "signData"; // in order secure for data that last you will valid it.

    String CHANNEL_ID = "channelId"; // not important after maybe useful.

    String CODE = "code"; // id or other ext.

    String TIME = "time";  // you can monitor gome request use it commonly.

    // 等 接口文档
}
