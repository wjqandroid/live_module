package com.visionvera.live.player.bean;

public class TCPlayKeyFrameDescInfo {
    public String content; // 描述信息
    public float time;// (秒)

    @Override
    public String toString() {
        return "TCPlayKeyFrameDescInfo{" +
                "content='" + content + '\'' +
                ", time=" + time +
                '}';
    }
}
