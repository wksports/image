package com.suyong.myglide.Glide;


import java.util.concurrent.LinkedBlockingQueue;

//管理请求队列
public class RequestManager {
    //请求队列
    private LinkedBlockingQueue <BitmapRequest> requestQueue ;

    private static RequestManager requestManager = new RequestManager();

    private BitmapDispatcher[] bitmapDispatchers;

    private RequestManager(){
        requestQueue = new LinkedBlockingQueue<>();
        stopAllThread();
        //初始化线程
        createAndStart();
    }

    private void createAndStart() {
        int count = Runtime.getRuntime().availableProcessors();
        bitmapDispatchers = new BitmapDispatcher[count];
        //创建线程
        for(int x =0;x<count;x++){
            BitmapDispatcher bitmapDispatcher = new BitmapDispatcher(requestQueue);
            bitmapDispatcher.start();
            bitmapDispatchers[x] = bitmapDispatcher;
        }
    }

    public static RequestManager getInstance(){
        return requestManager;
    }

    /**
     * 将请求对象添加到请求队列
     * @param request,
     */
    public void addBitmapRequest(BitmapRequest request){
        if(!requestQueue.contains(request)){
            requestQueue.add(request);
        }
    }

    public void stopAllThread(){
        if(bitmapDispatchers!=null && bitmapDispatchers.length > 0) {
            for (BitmapDispatcher bitmapDispatcher : bitmapDispatchers) {
                if(!bitmapDispatcher.isInterrupted()) bitmapDispatcher.isInterrupted();
            }
        }
    }
}
