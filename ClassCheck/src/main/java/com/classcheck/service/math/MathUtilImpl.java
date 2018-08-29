package com.classcheck.service.math;

import org.springframework.stereotype.Service;

/**
 * @Auther: 简单DI年华
 * @Date: 18-8-28 00:32
 * @Description:
 */
@Service
public class MathUtilImpl implements MathUtil{
        //private static double EARTH_RADIUS = 6378.137;
        private  double EARTH_RADIUS = 6371.393;
        private  double rad(double d)
        {
            return d * Math.PI / 180.0;
        }

        /**
         * 计算两个经纬度之间的距离
         * @param lat1
         * @param lng1
         * @param lat2
         * @param lng2
         * @return
         */
        @Override
        public  double getDistangce(double lng1, double lat1, double lng2, double lat2)
        {
            double radLat1 = rad(lat1);
            double radLat2 = rad(lat2);
            double a = radLat1 - radLat2;
            double b = rad(lng1) - rad(lng2);
            double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
                    Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
            s = s * EARTH_RADIUS;
            s = Math.round(s * 1000);
            return s;
        }


}
