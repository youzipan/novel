//package com.example.demo;
//
//import org.frameworkset.elasticsearch.ElasticSearchHelper;
//import org.frameworkset.tran.DataStream;
//import org.frameworkset.tran.db.input.es.DB2ESImportBuilder;
//
///**
// * @author Fengping.Pan
// * @Description:
// */
//public class Bboss {
//    public static void main(String[] args) {
//        DB2ESImportBuilder importBuilder = DB2ESImportBuilder.newInstance();
////        try {
////            //清除测试表数据
////            ElasticSearchHelper.getRestClientUtil().dropIndice("dbclobdemo");
////        }
////        catch (Exception e){
////
////        }
//        //数据源相关配置，可选项，可以在外部启动数据源
//        importBuilder.setDbName("hotel_resource")
//                .setDbDriver("com.mysql.jdbc.Driver") //数据库驱动程序，必须导入相关数据库的驱动jar包
//                .setDbUrl("jdbc:mysql://10.86.216.180:33306/hotel_resource?useCursorFetch=true") //通过useCursorFetch=true启用mysql的游标fetch机制，否则会有严重的性能隐患，useCursorFetch必须和jdbcFetchSize参数配合使用，否则不会生效
//                .setDbUser("hotel_resource")
//                .setDbPassword("7W@b3ZYb")
//                .setValidateSQL("select 1")
//                .setUsePool(false);//是否使用连接池
//
//
//        //指定导入数据的sql语句，必填项，可以设置自己的提取逻辑
//        importBuilder.setSql("select id,name,address from hr_hotel_pfp");
//        /**
//         * es相关配置
//         */
//        importBuilder
//                .setIndex("hotel") //必填项
////                .setIndexType("hotel") //es 7以后的版本不需要设置indexType，es7以前的版本必需设置indexType
//                .setRefreshOption(null)//可选项，null表示不实时刷新，importBuilder.setRefreshOption("refresh");表示实时刷新
//                .setUseJavaName(true) //可选项,将数据库字段名称转换为java驼峰规范的名称，例如:doc_id -> docId
//                .setBatchSize(5000)  //可选项,批量导入es的记录数，默认为-1，逐条处理，> 0时批量处理
//                .setJdbcFetchSize(10000);//设置数据库的查询fetchsize，同时在mysql url上设置useCursorFetch=true启用mysql的游标fetch机制，否则会有严重的性能隐患，jdbcFetchSize必须和useCursorFetch参数配合使用，否则不会生效
//
//
//        /**
//         * 执行数据库表数据导入es操作
//         */
//        DataStream dataStream = importBuilder.builder();
//        dataStream.execute();
//        dataStream.destroy();//执行完毕后释放资源
//        long count = ElasticSearchHelper.getRestClientUtil().countAll("hotel");
//        System.out.println(count);
//    }
//}
