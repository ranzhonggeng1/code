package com.wslint.wisdomreport.adpter;

import java.util.Map;

/**
 * TODO Image recognition imageInfo：文件基本信息 filePath: 图像文件的位置
 *
 * @author win 10
 * @since 2018/10/10 11:53
 */
public interface IRCGServerAdpater {

  Map<String, Object> recognitionImage(String imageInfo, String filePath);

  String getImageFilterKey();
}
