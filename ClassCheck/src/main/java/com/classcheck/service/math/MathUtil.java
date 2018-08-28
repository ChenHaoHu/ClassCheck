package com.classcheck.service.math;

import org.springframework.stereotype.Service;

/**
 * @Auther: 简单DI年华
 * @Date: 18-8-28 00:31
 * @Description:
 */
@Service
public interface MathUtil {
    double getDistangce(double lat1, double lng1, double lat2, double lng2);
}
