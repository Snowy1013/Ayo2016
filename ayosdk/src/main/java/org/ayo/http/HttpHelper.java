package org.ayo.http;

import org.ayo.LogInner;
import org.ayo.lang.Lists;
import org.ayo.lang.OnWalk;

import java.util.Map;


public class HttpHelper {
	
	
	public static final String HTTP_ERROR_INTO_PREFIX = "---http-fail---:";
	
	public static boolean isHttpCodeOK(int code){
		return code >= 200 && code < 300;
	}
	
	/**
	 * use ? & make a full url
	 * @param p_url
	 * @param params
	 * @return
	 */
	public static String makeURL(String p_url, Map<String, String> params) {
		StringBuilder url = new StringBuilder(p_url);
		if(url.indexOf("?")<0)
			url.append('?');

		for(String name : params.keySet()){
			url.append('&');
			url.append(name);
			url.append('=');
			url.append(String.valueOf(params.get(name)));
		}

		return url.toString().replace("?&", "?");
	}
	
//	/**
//	 * @param params
//	 * @return
//	 */
//	public static StringBuilder makeParamForPost(Map<String, String> params)
//	{
//		String boundary = "*****";
//		StringBuilder sb = new StringBuilder();
//        for (Entry<String, String> entry : params.entrySet())
//        {
//        	//构建表单字段内容
//            sb.append("--");
//            sb.append(boundary);
//            sb.append("\r\n");
//            sb.append("Content-Disposition: form-data; name=\""+ entry.getKey() + "\"\r\n\r\n");
//            sb.append(entry.getValue());
//            sb.append("\r\n");
//        }
//
//        sb.append("--");
//        sb.append(boundary);
//        sb.append("\r\n");
//        return sb;
//	}
//
//
//    /**
//     *
//     *
//     * @param params
//     * @return
//     */
//    public static List<org.apache.http.NameValuePair> buildNameValuePairs2(Map<String, String> params) {
//        Object[] keys = params.keySet().toArray();
//        List<org.apache.http.NameValuePair> pairs = new ArrayList<org.apache.http.NameValuePair>();
//
//        for (int i = 0; i < keys.length; i++) {
//            String key = (String) keys[i];
//            org.apache.http.NameValuePair p = new org.apache.http.message.BasicNameValuePair(key, params.get(key));
//            pairs.add(p);
//        }
//
//        return pairs;
//    }
//
//    public static String translateResponseStatusCode(int statusCode){
//
//		return "unknown http status code: " + statusCode;
//	}
//
//
//    public static String processInputStreamLikeString(InputStream in){
//    	String result = "";
//    	InputStreamReader inReader=new InputStreamReader(in);
//		BufferedReader buffer=new BufferedReader(inReader);
//		String strLine=null;
//		try {
//			while((strLine=buffer.readLine())!=null)
//			{
//				result+=strLine;
//			}
//			inReader.close();
//			return result;
//		} catch (IOException e) {
//			e.printStackTrace();
//			return e.getMessage();
//		}finally{
//			try {
//				inReader.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//
//    }
//
//    /**
//     * @param in
//     * @return
//     */
//    public static String processInputStreamLikeFile(InputStream in, String path){
//
//    	byte[] bs = new byte[1024];
//    	int len;
//    	OutputStream os;
//		try {
//			os = new FileOutputStream(path);
//	    	while ((len = in.read(bs)) != -1) {
//	    		os.write(bs, 0, len);
//	    	}
//
//	    	os.close();
//	    	in.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//    	return path;
//    }
//
//    //get domain of url
//  	public static String getDomainName(String url) {
//  		Pattern p = Pattern.compile("^http://[^/]+");
//          Matcher matcher = p.matcher(url);
//          if(matcher.find()){
//          	return  matcher.group();
//          }
//  		return "";
//  	}
//
//    public static String translateHttpCode(int httpCode){
//
//    	return "";
//    }
	
    public static void printMap(final Map<String, String> map){
    	try {
    		Lists.each(map.keySet(), new OnWalk<String>() {
    			
				@Override
				public boolean process(int index, String t, int total) {
    				String value = map.get(t)+"";
    				LogInner.debug(t + "==>" + value);
					return false;
				}
    		});
		} catch (Exception e) {
			//e.printStackTrace();
		}
    	
    }
}
