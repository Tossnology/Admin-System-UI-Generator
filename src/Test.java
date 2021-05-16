import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        String s = "分类管理";
        s=s.substring(0,s.length()-2);
        System.out.println(s);

        List<String> properties1 = Arrays.asList("ID" ,"属性名称");
        AdminTable propertyAdmin = new AdminTable("属性管理", properties1, null, null, true, true);
        propertyAdmin.setHtmlId("property");

        List<String> properties3 = Arrays.asList("ID" ,"产品单个图片缩略图");
        AdminTable imageAdmin = new AdminTable("图片管理", properties3, null, null, false, true);
        imageAdmin.setHtmlId("image_");

        List<String> properties2 = Arrays.asList("ID" ,"图片", "产品名称", "产品小标题", "原价格", "优惠价格", "库存数量");
        List<AdminTable> subtables2 = new ArrayList<>();
        subtables2.add(imageAdmin);
        List<String> operations2 = Arrays.asList("设置属性");
        AdminTable productAdmin = new AdminTable("产品管理", properties2, subtables2, operations2, true, true);
        productAdmin.setHtmlId("product");

        List<String> properties = Arrays.asList("ID" ,"图片", "分类名称");
        List<AdminTable> subtables = new ArrayList<>();
        subtables.add(propertyAdmin);
        subtables.add(productAdmin);
        AdminTable categoryAdmin = new AdminTable("分类管理", properties, subtables, null, true, true);
        categoryAdmin.setHtmlId("category");

        List<String> properties4 = Arrays.asList("ID" ,"用户名称");
        AdminTable userAdmin = new AdminTable("用户管理", properties4, null, null, false, false);
        userAdmin.setHtmlId("user");

        List<String> properties5 = Arrays.asList("ID" ,"状态", "金额", "商品数量", "买家名称", "创建时间", "支付时间", "发货时间", "确认收货时间");
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
