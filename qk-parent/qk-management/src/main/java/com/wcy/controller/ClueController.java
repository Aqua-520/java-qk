package com.wcy.controller;

import com.wcy.common.PageResponse;
import com.wcy.common.Response;
import com.wcy.dto.ClueMarkFalseDTO;
import com.wcy.dto.CluePoolDTO;
import com.wcy.dto.ClueQueryDTO;
import com.wcy.dto.ClueUpdateDTO;
import com.wcy.entity.Clue;
import com.wcy.service.ClueService;
import com.wcy.vo.CluePoolVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ClueController {
    private final ClueService clueService;

    // 新增线索
    @PostMapping("/clues")
    public Response addClue(@RequestBody Clue clue){
        // 设置状态为1,待分配
        clue.setStatus(1);
        // 新增到数据库
        boolean result = this.clueService.save(clue);

        return result ? Response.success() : Response.error("新增线索失败");
    }

    // 查询线索列表,分页版
    @GetMapping("/clues")
    public Response selectClueListByLimit(ClueQueryDTO clueQueryDTO){
        // 将接收来的dto丢到业务层
        // 返回分页的对象
        PageResponse<Clue> cluePageResponse = this.clueService.selectClueListByLimit(clueQueryDTO);

        return Response.success(cluePageResponse);
    }

    // 分配线索的函数
    @PutMapping("/clues/assign/{clueId}/{userId}")
    public Response assignClue(@PathVariable Integer clueId,
                               @PathVariable Integer userId){
        // 调用业务层,让某个老师来跟进这条线索
        this.clueService.assignClue(clueId,userId);

        return Response.success();
    }

    // 根据id查询线索
    @GetMapping("/clues/{id}")
    public Response selectClueById(@PathVariable("id") Integer clueId){
        // 调用业务层返回clue对象返回给前端
        Clue clue = this.clueService.selectClueById(clueId);
        return Response.success(clue);
    }

    // 线索跟进,也就是修改,补充id查询到的线索
    @PutMapping("/clues")
    public Response updateClue(@RequestBody ClueUpdateDTO clueUpdateDTO){
        // 调用业务层方法,完成更新及其创建跟进记录的操作
        this.clueService.updateClue(clueUpdateDTO);
        return Response.success();
    }

    // 将线索转商机的接口
    @PutMapping("/clues/toBusiness/{id}")
    public Response clueToBusiness(@PathVariable("id") Integer clueId){
        // 将id发给业务层做操作,主要是修改状态,并且创建新的商机对象存储数据库中
        this.clueService.clueToBusiness(clueId);

        return Response.success();
    }

    // 线索转伪线索处理
    @PutMapping("/clues/false/{id}")
    public Response clueToFalse(@PathVariable("id") Integer clueId, @RequestBody ClueMarkFalseDTO clueMarkFalseDTO){
        // 将id发给业务层做操作,主要是修改状态,并且生成新的跟进记录
        this.clueService.clueToFalse(clueId,clueMarkFalseDTO);

        return Response.success();
    }

    // 线索池列表查询
    @GetMapping("/clues/pool")
    public Response selectCluePool(CluePoolDTO cluePoolDTO){
        // 将DTO传给业务层,返回分页响应对象
        PageResponse<CluePoolVO> pageResponse = this.clueService.selectCluePool(cluePoolDTO);

        return Response.success(pageResponse);
    }

}
