package com.wcy.controller;

import com.wcy.common.PageResponse;
import com.wcy.common.Response;
import com.wcy.dto.ClueQueryDTO;
import com.wcy.entity.Clue;
import com.wcy.service.ClueService;
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
}
