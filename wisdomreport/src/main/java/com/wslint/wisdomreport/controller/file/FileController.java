package com.wslint.wisdomreport.controller.file;

import com.wslint.wisdomreport.domain.vo.file.ImgFileGetVO;
import com.wslint.wisdomreport.service.file.IFileService;
import com.wslint.wisdomreport.utils.ReturnUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文件控制controller
 *
 * @author yuxr
 * @since 2018/11/7 14:55
 */
@Api(tags = "4 文件接口", description = "提供文件管理的相关接口")
@RestController
@RequestMapping(value = "/file")
public class FileController {

  @Autowired IFileService iFileService;

  @ApiOperation(value = "图片文件获取接口", notes = "图片文件获取")
  @ApiImplicitParam(
      name = "imgFileGetVO",
      value = "图片文件信息",
      required = true,
      dataType = "ImgFileGetVO")
  @RequestMapping(value = "/img", method = RequestMethod.POST)
  public Map<String, Object> img(@RequestBody ImgFileGetVO imgFileGetVO) {
    if (imgFileGetVO.getMedicineId() == null
        || imgFileGetVO.getBatchNo() == null
        || imgFileGetVO.getFirstClassId() == null
        || imgFileGetVO.getSecondClassId() == null) {
      return ReturnUtils.failureMap("传入参数为空！");
    }
    List<String> imgUrls =
        iFileService.getImgUrls(
            imgFileGetVO.getMedicineId(),
            imgFileGetVO.getBatchNo(),
            imgFileGetVO.getFirstClassId(),
            imgFileGetVO.getSecondClassId());
    List<String> proofUrls =
        iFileService.getProofUrls(
            imgFileGetVO.getMedicineId(),
            imgFileGetVO.getBatchNo(),
            imgFileGetVO.getFirstClassId(),
            imgFileGetVO.getSecondClassId());
    List<String> rtnList = new ArrayList<>();
    rtnList.addAll(imgUrls);
    rtnList.addAll(proofUrls);
    return ReturnUtils.successMap(rtnList, "图片文件url获取成功！");
  }
}
