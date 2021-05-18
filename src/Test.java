import java.util.*;

public class Test {
    public static void main(String[] args) {
        String s = "分类管理";
        s=s.substring(0,s.length()-2);
        System.out.println(s);

        List<String[]> properties1 = new ArrayList<>();
        properties1.add(new String[]{"id", "ID"});
        properties1.add(new String[]{"name", "属性名称"});
        AdminTable propertyAdmin = new AdminTable("属性管理", properties1, null, null, true, true);
        propertyAdmin.setHtmlId("property");

        List<String[]> properties3 = new ArrayList<>();
        properties3.add(new String[]{"id", "ID"});
        properties3.add(new String[]{"simple", "产品单个图片缩略图"});
        AdminTable imageAdmin = new AdminTable("图片管理", properties3, null, null, false, true);
        imageAdmin.setHtmlId("image_");

        List<String[]> properties2 = new ArrayList<>();
        properties2.add(new String[]{"id", "ID"});
        properties2.add(new String[]{"image", "图片"});
        properties2.add(new String[]{"name", "产品名称"});
        properties2.add(new String[]{"stitle", "产品小标题"});
        properties2.add(new String[]{"oprice", "原价格"});
        properties2.add(new String[]{"lprice", "优惠价格"});
        properties2.add(new String[]{"number", "库存数量"});
        List<AdminTable> subtables2 = new ArrayList<>();
        subtables2.add(imageAdmin);
        List<String> operations2 = Arrays.asList("设置属性");
        AdminTable productAdmin = new AdminTable("产品管理", properties2, subtables2, operations2, true, true);
        productAdmin.setHtmlId("product");

        List<String[]> properties = new ArrayList<>();
        properties.add(new String[]{"id", "ID"});
        properties.add(new String[]{"image", "图片"});
        properties.add(new String[]{"name", "分类名称"});
        List<AdminTable> subtables = new ArrayList<>();
        subtables.add(propertyAdmin);
        subtables.add(productAdmin);
        AdminTable categoryAdmin = new AdminTable("分类管理", properties, subtables, null, false, true);
        categoryAdmin.setHtmlId("category");

        List<String[]> properties4 = new ArrayList<>();
        properties4.add(new String[]{"id", "ID"});
        properties4.add(new String[]{"name", "用户名称"});
        AdminTable userAdmin = new AdminTable("用户管理", properties4, null, null, false, false);
        userAdmin.setHtmlId("user");

        List<String[]> properties5 = new ArrayList<>();
        properties5.add(new String[]{"id", "ID"});
        properties5.add(new String[]{"status", "状态"});
        properties5.add(new String[]{"pay", "金额"});
        properties5.add(new String[]{"count", "商品数量"});
        properties5.add(new String[]{"buyerName", "买家名称"});
        properties5.add(new String[]{"createTime", "创建时间"});
        properties5.add(new String[]{"payTime", "支付时间"});
        properties5.add(new String[]{"deliverTime", "发货时间"});
        properties5.add(new String[]{"confirmTime", "确认收货时间"});
        List<String> operations5 = Arrays.asList("查看详情", "发货");
        AdminTable orderAdmin = new AdminTable("订单管理", properties5, null, operations5, false, false);
        orderAdmin.setHtmlId("order");

        List<AdminTable> navTables = new ArrayList<>();
        navTables.add(categoryAdmin);
        navTables.add(userAdmin);
        navTables.add(orderAdmin);

        Generator.generate("电商后台管理系统", navTables);
    }
}
