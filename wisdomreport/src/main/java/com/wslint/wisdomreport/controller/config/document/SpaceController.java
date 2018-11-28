package com.wslint.wisdomreport.controller.config.document;

import com.wslint.wisdomreport.domain.dto.xml.secondclass.SpaceDTO;
import com.wslint.wisdomreport.domain.vo.xml.SpaceVO;
import com.wslint.wisdomreport.service.config.document.ISpaceService;
import com.wslint.wisdomreport.utils.ReturnUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 空格接口
 *
 * @author yuxr
 * @since 2018/10/31 10:47
 */
@Api(tags = "5 文档配置接口", description = "提供文档配置的相关接口")
@RestController
@RequestMapping(value = "/config/document/space")
public class SpaceController {

  @Autowired ISpaceService iSpaceService;
  /**
   * 根据药品id、大类id、小类id和空格id获取空格信息
   *
   * @return 小类信息
   */
  @ApiOperation(value = "获取空格信息", notes = "获取空格信息")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "medicineId", value = "药品id", required = true, defaultValue = "1"),
    @ApiImplicitParam(name = "secondClassId", value = "小类id", required = true, defaultValue = "12"),
    @ApiImplicitParam(name = "spaceId", value = "空格id", required = true, defaultValue = "54")
  })
  @RequestMapping(value = "/get", method = RequestMethod.GET)
  public Map<String, Object> get(
      @RequestParam Long medicineId, @RequestParam Long secondClassId, @RequestParam Long spaceId) {

    // 空格信息
    SpaceDTO spaceDTO =
        iSpaceService.getSpaceByMedicineSecondClassSpaceId(medicineId, secondClassId, spaceId);

    return ReturnUtils.successMap(spaceDTO, "获取空格信息成功！");
  }
  /**
   * 根据药品id、大类id、小类id和空格id保存空格信息
   *
   * @return 小类信息
   */
  @ApiOperation(value = "保存空格信息", notes = "保存空格信息")
  @RequestMapping(value = "/save", method = RequestMethod.POST)
  public Map<String, Object> save(@RequestBody SpaceVO spaceVO) {
    SpaceDTO spaceDTO = new SpaceDTO();
    BeanCopier copier = BeanCopier.create(SpaceVO.class, SpaceDTO.class, false);
    copier.copy(spaceVO, spaceDTO, null);
    return iSpaceService.saveSpace(spaceVO.getMedicineId(), spaceVO.getSecondClassId(), spaceDTO);
  }
}
