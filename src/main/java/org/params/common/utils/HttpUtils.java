package org.params.common.utils;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;
import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * 请求工具类
 *
 * @Author: 黎宇
 */
@Slf4j
public class HttpUtils {
    private HttpUtils() {
    }

    /**
     * 连接池管理对象
     */
    private static PoolingHttpClientConnectionManager connectionManager;
    /**
     * 连接超时毫秒 ps：表示建立连接的超时时间
     */
    private final static int CONNECT_TIMEOUT = 3000;
    /**
     * 传输超时毫秒 ps：表示数据传输处理时间
     */
    private final static int SOCKET_TIMEOUT = 10000;
    /**
     * 从线程池获取连接超时时间毫秒
     */
    private final static int REQUEST_CONNECTION_TIMED_OUT = 20000;
    /**
     * 线程池的最大连接数
     */
    private final static int MAX_TOTAL = 10000;
    /**
     * 每个路由默认基础的连接数
     */
    private final static int CONNECT_DEFAULT_ROUTE = 200;

    /**
     * 初始化连接池
     */
    public static synchronized void init() {
        if (connectionManager == null) {
            connectionManager = new PoolingHttpClientConnectionManager();
            // 整个连接池最大连接数
            connectionManager.setMaxTotal(MAX_TOTAL);
            // 每路由最大连接数，默认值是2
            connectionManager.setDefaultMaxPerRoute(CONNECT_DEFAULT_ROUTE);
        }
    }

    /**
     * 通过连接池获取HttpClient
     *
     * @return 可关闭的 Http 客户端
     */
    private static CloseableHttpClient getHttpClient() {
        init();
        RequestConfig.Builder builder = RequestConfig.custom();
        RequestConfig config = builder.setSocketTimeout(SOCKET_TIMEOUT)
                .setConnectTimeout(CONNECT_TIMEOUT)
                .setConnectionRequestTimeout(REQUEST_CONNECTION_TIMED_OUT)
                .build();
        return HttpClients.custom()
                .setMaxConnPerRoute(CONNECT_DEFAULT_ROUTE)
                .disableConnectionState().setDefaultRequestConfig(config)
                .setConnectionManager(connectionManager).build();
    }


    private static CloseableHttpClient getHttpClientV2(Integer lnTime) {
        init();
        RequestConfig.Builder builder = RequestConfig.custom();
        RequestConfig config = builder.setSocketTimeout(lnTime)
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(REQUEST_CONNECTION_TIMED_OUT)
                .build();
        return HttpClients.custom()
                .setMaxConnPerRoute(CONNECT_DEFAULT_ROUTE)
                .disableConnectionState().setDefaultRequestConfig(config)
                .setConnectionManager(connectionManager).build();
    }

    /**
     * 向指定 URL 发送GET方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        return sendGet(url, param, "UTF-8");
    }

    /**
     * 向指定 URL 发送GET方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param token 请求授权的token
     * @return 所代表远程资源的响应结果
     */
    public static String sendTokenGet(String url, String param, String token) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            log.info("sendGet - {}", urlNameString);
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("token", token);
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            log.info("recv - {}", result);
        } catch (ConnectException e) {
            log.error("调用HttpUtils.sendGet ConnectException, url=" + url + ",param=" + param, e);
        } catch (SocketTimeoutException e) {
            log.error("调用HttpUtils.sendGet SocketTimeoutException, url=" + url + ",param=" + param, e);
        } catch (IOException e) {
            log.error("调用HttpUtils.sendGet IOException, url=" + url + ",param=" + param, e);
        } catch (Exception e) {
            log.error("调用HttpsUtil.sendGet Exception, url=" + url + ",param=" + param, e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception ex) {
                log.error("调用in.close Exception, url=" + url + ",param=" + param, ex);
            }
        }
        return result.toString();
    }

    /**
     * 向指定 URL 发送GET方法的请求
     *
     * @param url         发送请求的 URL
     * @param param       请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param contentType 编码类型
     * @return 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param, String contentType) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            log.info("sendGet - {}", urlNameString);
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), contentType));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            log.info("recv - {}", result);
        } catch (ConnectException e) {
            log.error("调用HttpUtils.sendGet ConnectException, url=" + url + ",param=" + param, e);
        } catch (SocketTimeoutException e) {
            log.error("调用HttpUtils.sendGet SocketTimeoutException, url=" + url + ",param=" + param, e);
        } catch (IOException e) {
            log.error("调用HttpUtils.sendGet IOException, url=" + url + ",param=" + param, e);
        } catch (Exception e) {
            log.error("调用HttpsUtil.sendGet Exception, url=" + url + ",param=" + param, e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception ex) {
                log.error("调用in.close Exception, url=" + url + ",param=" + param, ex);
            }
        }
        return result.toString();
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            if (!url.contains("47.96.236.136:7862")) {
                log.info("sendPost - {}", url);
            }
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", "utf-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            log.info("recv - {}", result);
        } catch (ConnectException e) {
            log.error("调用HttpUtils.sendPost ConnectException, url=" + url + ",param=" + param, e);
        } catch (SocketTimeoutException e) {
            log.error("调用HttpUtils.sendPost SocketTimeoutException, url=" + url + ",param=" + param, e);
        } catch (IOException e) {
            log.error("调用HttpUtils.sendPost IOException, url=" + url + ",param=" + param, e);
        } catch (Exception e) {
            log.error("调用HttpsUtil.sendPost Exception, url=" + url + ",param=" + param, e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                log.error("调用in.close Exception, url=" + url + ",param=" + param, ex);
            }
        }
        return result.toString();
    }

    public static String sendSSLPost(String url, String param) {
        StringBuilder result = new StringBuilder();
        String urlNameString = url + "?" + param;
        try {
            log.info("sendSSLPost - {}", urlNameString);
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
            URL console = new URL(urlNameString);
            HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", "utf-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setSSLSocketFactory(sc.getSocketFactory());
            conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String ret = "";
            while ((ret = br.readLine()) != null) {
                if (ret != null && !"".equals(ret.trim())) {
                    result.append(new String(ret.getBytes("ISO-8859-1"), "utf-8"));
                }
            }
            log.info("recv - {}", result);
            conn.disconnect();
            br.close();
        } catch (ConnectException e) {
            log.error("调用HttpUtils.sendSSLPost ConnectException, url=" + url + ",param=" + param, e);
        } catch (SocketTimeoutException e) {
            log.error("调用HttpUtils.sendSSLPost SocketTimeoutException, url=" + url + ",param=" + param, e);
        } catch (IOException e) {
            log.error("调用HttpUtils.sendSSLPost IOException, url=" + url + ",param=" + param, e);
        } catch (Exception e) {
            log.error("调用HttpsUtil.sendSSLPost Exception, url=" + url + ",param=" + param, e);
        }
        return result.toString();
    }

    private static class TrustAnyTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    public static String httpPostRequest(String url, String jsonParams) {
        try {
            HttpPost httpPost = new HttpPost(url);
            StringEntity se = new StringEntity(jsonParams, StandardCharsets.UTF_8);
            httpPost.setEntity(se);
            httpPost.setHeader("Content-Type", "application/json");
            return getResult(httpPost);
        } catch (Exception e) {
            log.error("请求链接:{},请求参数:{}异常",url,jsonParams,e);
        }
        return null;
    }

    /**
     * 带请求头的HttpPOST请求
     *
     * @param url       请求链接
     * @param jsonParams 请求参数
     * @param head     请求头
     * @return 结果
     */
    public static String httpPostRequestHead(String url, String jsonParams, Map<String, String> head) {
        try {
            HttpPost httpPost = new HttpPost(url);
            StringEntity se = new StringEntity(jsonParams, StandardCharsets.UTF_8);
            httpPost.setEntity(se);
            httpPost.setHeader("Content-Type", "application/json");
            if (head != null) {
                for (String s : head.keySet()) {
                    httpPost.setHeader(s, head.get(s));
                }
            }

            return getResult(httpPost);
        } catch (Exception e) {
            log.error("请求链接:{},请求参数:{}异常",url,jsonParams,e);
        }
        return null;
    }

    public static String httpPostRequestHeadV2(String url, String jsonParams, Map<String, String> head, Integer lnTime) {
        try {
            HttpPost httpPost = new HttpPost(url);
            StringEntity se = new StringEntity(jsonParams, StandardCharsets.UTF_8);
            httpPost.setEntity(se);
            httpPost.setHeader("Content-Type", "application/json");
            if (head != null) {
                for (String s : head.keySet()) {
                    httpPost.setHeader(s, head.get(s));
                }
            }

            return getResultV2(httpPost,lnTime);
        } catch (Exception e) {
            log.error("请求链接:{},请求参数:{}异常",url,jsonParams,e);
        }
        return null;
    }


    private static String getResultV2(HttpRequestBase request, Integer lnTime) {
        CloseableHttpClient httpClient = getHttpClientV2(lnTime);
        CloseableHttpResponse response = null;
        InputStream in = null;
        log.info("总连接: {}", connectionManager.getTotalStats().getLeased());
        log.info("可用连接: {}", connectionManager.getTotalStats().getAvailable());
        try {
            response = httpClient.execute(request);
//            log.info("HttpUtils   getResult--------response----->{}", JSON.toJSONString(response));
            HttpEntity entity = response.getEntity();
            in = response.getEntity().getContent();
            if (entity != null) {
                String result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
                response.close();
                return result;
            }
        } catch (ConnectTimeoutException e) {
            // 连接超时异常
            log.error("http异常V2, 请求链接超时, url:{}", request.getURI(), e);
        } catch (SocketTimeoutException e) {
            // 读取超时异常
            log.error("http异常V2, 读取超时异常, url:{},连接数：{}", request.getURI(),connectionManager.getTotalStats().getAvailable(), e);
        } catch (ClientProtocolException e) {
            // 该异常通常是协议错误导致:比如构造HttpGet对象时传入协议不对(将'http'写成'htp')or响应内容不符合
            log.error("http异常V2, 协议错误导致, url:{}", request.getURI(), e);
        } catch (ParseException e) {
            // 解析异常
            log.error("http异常V2, 解析异常, url:{}", request.getURI(), e);
        } catch (IOException e) {
            // 该异常通常是网络原因引起的,如HTTP服务器未启动等
            log.error("http异常V2, 网络原因引起的, url:{}", request.getURI(), e);
        } catch (Exception e) {
            log.error("http异常V2, 请求异常, url:{}", request.getURI(), e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    log.error("http异常V2, 关闭response异常", e);
                }
            }
            //in.close();作用就是将用完的连接释放，下次请求可以复用
            //这里特别注意的是，如果不使用in.close();而仅仅使用response.close();结果就是连接会被关闭，并且不能被复用，这样就失去了采用连接池的意义。
//            if (in != null) {
//                try {
//                    in.close();
//                } catch (IOException e) {
//                    log.error("http异常V2, 关闭in异常", e);
//                }
//            }
        }

        return StrUtil.EMPTY;
    }


    /**
     * 处理Http请求
     *
     * @param request 请求
     * @return string
     */
    private static String getResult(HttpRequestBase request) {
        CloseableHttpClient httpClient = getHttpClient();
        CloseableHttpResponse response = null;
        InputStream in = null;
        try {
            response = httpClient.execute(request);
//            log.info("HttpUtils   getResult--------response----->{}", JSON.toJSONString(response));
            HttpEntity entity = response.getEntity();
            in = response.getEntity().getContent();
            if (entity != null) {
                String result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
                response.close();
                return result;
            }
        } catch (ConnectTimeoutException e) {
            // 连接超时异常
            log.error("http异常, 请求链接超时, url:{}", request.getURI(), e);
        } catch (SocketTimeoutException e) {
            // 读取超时异常
            log.error("http异常, 读取超时异常, url:{}", request.getURI(), e);
        } catch (ClientProtocolException e) {
            // 该异常通常是协议错误导致:比如构造HttpGet对象时传入协议不对(将'http'写成'htp')or响应内容不符合
            log.error("http异常, 协议错误导致, url:{}", request.getURI(), e);
        } catch (ParseException e) {
            // 解析异常
            log.error("http异常, 解析异常, url:{}", request.getURI(), e);
        } catch (IOException e) {
            // 该异常通常是网络原因引起的,如HTTP服务器未启动等
            log.error("http异常, 网络原因引起的, url:{}", request.getURI(), e);
        } catch (Exception e) {
            log.error("http异常, 请求异常, url:{}", request.getURI(), e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    log.error("http异常, 关闭response异常", e);
                }
            }
            //in.close();作用就是将用完的连接释放，下次请求可以复用
            //这里特别注意的是，如果不使用in.close();而仅仅使用response.close();结果就是连接会被关闭，并且不能被复用，这样就失去了采用连接池的意义。
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("http异常, 关闭in异常", e);
                }
            }
        }

        return StrUtil.EMPTY;
    }




    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url     发送请求的 URL
     * @param headers 请求头
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String jsonData, Map<String, Object> headers) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection con = realUrl.openConnection();
            HttpURLConnection conn = (HttpURLConnection) con;
            // 设置通用的请求属性
            conn.setRequestMethod("POST"); // 设置Post请求
            conn.setConnectTimeout(5 * 1000);
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/json");
//            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); // 设置内容类型
            // 添加用户设置的请求头
            if (headers != null) {
                Set<Map.Entry<String, Object>> headerEntrySet = headers.entrySet();
                for (Map.Entry<String, Object> entry : headerEntrySet) {
                    conn.setRequestProperty(entry.getKey(), entry.getValue().toString());
                }
            }
            // conn.setRequestProperty("Content-Length",
            // String.valueOf(param.length())); //设置长度
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "utf-8"));
            // 发送请求参数
            // out.print(param);
            out.write(jsonData);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            byte[] bresult = result.getBytes();
            result = new String(bresult, "utf-8");
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }


    /**
     * POST请求
     */
    public static String doPost(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (null != param) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "UTF-8");
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != response) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    /**
     * POST请求
     */
    public static String doPostAi(String url, Map<String, Object> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (null != param) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key).toString()));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "UTF-8");
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != response) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    /**
     * 向face++ 发送POST方法的请求 form-data格式
     *
     * @param url     发送请求的 URL
     * @return 所代表远程资源的响应结果
     */
    @SuppressWarnings("rawtypes")
    public static String sendPostFormData(String urlStr, Map<String, String> textMap,
                                    Map<String, String> fileMap) {
        String res = "";
        HttpURLConnection conn = null;
        String contentType = "application/octet-stream";
        String BOUNDARY = "-----------------12345654321-----------";
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + BOUNDARY);
            conn.setRequestProperty("Charset", "UTF-8");
            OutputStream out = new DataOutputStream(conn.getOutputStream());
            if (textMap != null) {
                StringBuffer strBuf = new StringBuffer();
                Iterator iter = textMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    strBuf.append("\r\n").append("--").append(BOUNDARY)
                            .append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\""
                            + inputName + "\"\r\n\r\n");
                    strBuf.append(inputValue);
                }
                out.write(strBuf.toString().getBytes("UTF-8"));
            }
            if (fileMap != null) {
                Iterator iter = fileMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    File file = new File(inputValue);
                    String filename = file.getName();
                    StringBuffer strBuf = new StringBuffer();
                    strBuf.append("\r\n").append("--").append(BOUNDARY)
                            .append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\""
                            + inputName + "\"; filename=\"" + filename
                            + "\"\r\n");
                    strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
                    out.write(strBuf.toString().getBytes());
                    DataInputStream in = new DataInputStream(
                            new FileInputStream(file));
                    int bytes = 0;
                    byte[] bufferOut = new byte[1024];
                    while ((bytes = in.read(bufferOut)) != -1) {
                        out.write(bufferOut, 0, bytes);
                    }
                    in.close();
                }
            }
            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            out.close();
            int responseCode = conn.getResponseCode();
            if (responseCode==200) {
                // 读取返回数据
                StringBuffer strBuf = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        conn.getInputStream(),"UTF-8"));

                String line = null;
                while ((line = reader.readLine()) != null) {
                    strBuf.append(line).append("\n");
                }
                res = strBuf.toString();
                reader.close();
                reader = null;
            }else{
                StringBuffer error = new StringBuffer();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                        conn.getErrorStream(),"UTF-8"));
                String line1 = null;
                while ((line1=bufferedReader.readLine())!=null) {
                    error.append(line1).append("\n");
                }
                res=error.toString();
                bufferedReader.close();
                bufferedReader=null;
            }

        } catch (Exception e) {
            System.out.println("发送POST请求出错。" + e);
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        return res;
    }

}
