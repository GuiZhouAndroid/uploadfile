package com.example.uploadfile.service.serviceimpl;

import com.example.uploadfile.mapper.TestMapper;
import com.example.uploadfile.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created: by 2022-09-09 12:38
 * Author: 张松
 * Description:
 */

@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private TestMapper testMapper;

    @Override
    public int addUrl(String url) {
        return testMapper.addUrl(url);
    }
}
