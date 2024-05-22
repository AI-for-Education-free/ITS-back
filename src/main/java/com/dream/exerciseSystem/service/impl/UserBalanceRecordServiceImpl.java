package com.dream.exerciseSystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dream.exerciseSystem.domain.UserBalanceRecord;
import com.dream.exerciseSystem.mapper.UserBalanceRecordMapper;
import com.dream.exerciseSystem.service.UserBalanceRecordService;
import org.springframework.stereotype.Service;

/**
* @author xzy
* @description 针对表【UserBalanceRecord(store the user balance)】的数据库操作Service实现
* @createDate 2024-05-21 17:30:30
*/
@Service
public class UserBalanceRecordServiceImpl extends ServiceImpl<UserBalanceRecordMapper, UserBalanceRecord>
implements UserBalanceRecordService {

}
