import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Generator {
    public static boolean createFile(String destFileName){

        File file = new File(destFileName);//根据指定的文件名创建File对象

        //要创建的单个文件已存在
        if ( file.exists()){
            System.out.println("文件"+destFileName+"已存在，创建失败！" );
            return false;
        }


        //如果输入的文件是以分隔符结尾的，则说明File对象是目录而不是文件
        if ( destFileName.endsWith(File.separator)){
            System.out.println("文件"+destFileName+"是目录，目标文件不能是目录，创建失败！" );
            return false;
        }

        //判断目标文件所在目录是否存在
        if (!file.getParentFile().exists()){//如果目标文件所在文件夹不存在，则创建父文件夹
            System.out.println("创建"+file.getName()+"所在目录不存在，正在创建！" );

            //判断父文件夹是否存在，如果存在则表示创建成功，否则失败
            if ( !file.getParentFile().mkdirs() ){
                System.out.println("创建目标文件所在目录失败！" );
                return false;
            }
        }


        //创建目标文件
        try{
            if ( file.createNewFile() ){
                System.out.println("创建单个文件"+destFileName+"成功！" );
                return true;
            }else{
                System.out.println("创建单个文件"+destFileName+"失败！" );
                return false;
            }
        }catch(IOException e){//IOException异常需引入java.io.IOException包
            e.printStackTrace(); //在命令行打印异常信息在程序中出错的位置及原因。
            System.out.println("创建单个文件"+destFileName+"失败！" +e.getMessage());//e.getMessage()只会获得具体的异常名称
            return false;
        }


    }

    public static void generate(String projectName, List<AdminTable> navTables) {
        //构造界面代码，对Logo和导航栏赋值
        buildIndex(projectName, navTables);
        for (int i = 0; i < navTables.size(); i++) {
            generateHTML(navTables.get(i));
        }
    }

    public static void generateHTML(AdminTable table) {
        System.out.println(table.getTableName() + ".html");
        System.out.println("表名：" + table.getTableName());
        System.out.println("字段：" + table.getProperties());

        if (table.getSubTables() != null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < table.getSubTables().size(); i++) {
                sb.append(table.getSubTables().get(i).getTableName() + " ");
            }
            System.out.println("子表：" + sb);
        } else {
            System.out.println("子表：" + table.getSubTables());
        }

        System.out.println("操作：" + table.getOperations());
        System.out.println("可编辑：" + table.isEditEnable());
        System.out.println("可删除：" + table.isDeleteEnable());
        System.out.println(" ");

        buildTable(table);

        if (table.getSubTables() != null) {
            for (int i = 0; i < table.getSubTables().size(); i++) {
                table.getSubTables().get(i).setSub(true);
                generateHTML(table.getSubTables().get(i));
            }
        }
    }

    public static void buildIndex(String projectName, List<AdminTable> navTables) {
        String path = "./output/" + projectName + ".html";
        createFile(path);
        String htmlCode = buildIndexHTMLCode(projectName, navTables);
        writeFileString(path, htmlCode);
    }

    public static void buildTable(AdminTable table) {
        String path = "./output/" + table.getTableName() + ".html";
        createFile(path);
        String htmlCode = buildTableHTMLCode(table);
        writeFileString(path, htmlCode);
    }

    public static void writeFileString(String path, String content) {
        try {
            FileWriter fw = new FileWriter(path);//FileWriter写入文件时不能指定编码格式，编码格式是系统默认的编码格式
            fw.write(content); //向文件中写入字符串
            fw.flush(); //刷新
            fw.close(); //关闭流
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String buildIndexHTMLCode(String projectName, List<AdminTable> navTables) {
        String str = "";
        str += "<!DOCTYPE html>";
        str += "<html lang=\"en\">";
        str += "<head>";
        str += "    <meta charset=\"UTF-8\">";
        str += "    <title>后台管理</title>";
        str += "    <link rel=\"stylesheet\" href=\"layui/css/layui.css\">";
        str += "    <script src=\"layui/layui.js\"></script>";
        str += "    <style>";
        str += "        /*对layui中的样式进行重写*/";
        str += "        .layui-tab-content {";
        str += "            padding: 0;/*layui-tab-content:默认有padding: 10px;的值，因为iframe的绝对定位脱离文档流，所以会存在20px的空白空间*/";
        str += "        }";
        str += "        .show-frame {";
        str += "            top: 50px!important;";
        str += "            /*默认.layui-layout-admin .layui-body {";
        str += "                top: 60px;";
        str += "                bottom: 44px;";
        str += "            }*/";
        str += "            overflow: hidden;/*消除浏览器最右边的滚动条*/";
        str += "        }";
        str += "        .frame {";
        str += "            position: absolute;";
        str += "            padding: 10px;/*与layui-footer隔开一段距离*/";
        str += "            width: 100%;";
        str += "            height: 100%;";
        str += "        }";
        str += "    </style>";
        str += "</head>";
        str += "<body>";
        str += "<div class=\"layui-layout layui-layout-admin\">";
        str += "    <div class=\"layui-header\">";
        str += "        <div class=\"layui-logo\">"+projectName+"</div>";
        str += "        <!-- 头部区域（可配合layui已有的水平导航） -->";
        str += "        <!-- <ul class=\"layui-nav layui-layout-left\">";
        str += "            <li class=\"layui-nav-item\"><a href=\"\">控制台</a></li>";
        str += "            <li class=\"layui-nav-item\"><a href=\"\">商品管理</a></li>";
        str += "            <li class=\"layui-nav-item\"><a href=\"\">用户</a></li>";
        str += "            <li class=\"layui-nav-item\">";
        str += "                <a href=\"javascript:;\">其它系统</a>";
        str += "                <dl class=\"layui-nav-child\">";
        str += "                    <dd><a href=\"\">邮件管理</a></dd>";
        str += "                    <dd><a href=\"\">消息管理</a></dd>";
        str += "                    <dd><a href=\"\">授权管理</a></dd>";
        str += "                </dl>";
        str += "            </li>";
        str += "        </ul> -->";
        str += "        <ul class=\"layui-nav layui-layout-right\">";
        str += "            <li class=\"layui-nav-item\" lay-unselect=\"\">";
        str += "                <a href=\"javascript:;\"><img src=\"./luffy.jpeg\" class=\"layui-nav-img\">我</a>";
        str += "                <dl class=\"layui-nav-child\">";
        str += "                    <dd><a href=\"javascript:;\">修改信息</a></dd>";
        str += "                    <dd><a href=\"javascript:;\">退出登录</a></dd>";
        str += "                </dl>";
        str += "            </li>";
        str += "        </ul>";
        str += "    </div>";
        str += "";
        str += "    <div class=\"layui-side layui-bg-black\">";
        str += "        <div class=\"layui-side-scroll\">";
        str += "            <!-- 左侧导航区域（可配合layui已有的垂直导航） -->";
        str += "            <ul class=\"layui-nav layui-nav-tree\"  lay-filter=\"test\">";
        str += "                <li class=\"layui-nav-item layui-nav-itemed\">";
        str += "                    <a class=\"\" href=\"javascript:;\">模块管理</a>";
        str += "                    <dl class=\"layui-nav-child\">";
        for (int i = 0; i < navTables.size(); i++) {
            str += "<dd><a href=\"javascript:;\" name=\"t1\" id=\"" + i + "\" title=\"" + navTables.get(i).getTableName() + "\" content=\"" + navTables.get(i).getTableName() + ".html\">" + navTables.get(i).getTableName() + "</a></dd>";
        }
        str += "                    </dl>";
        str += "                </li>";
        str += "            </ul>";
        str += "        </div>";
        str += "    </div>";
        str += "";
        str += "    <div class=\"layui-body show-frame\">";
        str += "        <div class=\"layui-tab layui-tab-brief\" lay-allowClose=\"true\" lay-filter=\"empTab\">";
        str += "        <ul class=\"layui-tab-title\">";
        str += "            <li class=\"layui-this \"><i class=\"layui-icon\">&#xe68e;</i></li>";
        str += "        </ul>";
        str += "        <div class=\"layui-tab-content\">";
        str += "            <div class=\"layui-tab-item layui-show\"><h1 style=\"color: #5FB878\">欢迎登录" + projectName + "</h1></div>";
        str += "        </div>";
        str += "    </div>";
        str += "    </div>";
        str += "";
        str += "    <div class=\"layui-footer\">";
        str += "        <!-- 底部固定区域 -->";
        str += "        © yrxx.com - 底部固定区域";
        str += "    </div>";
        str += "</div>";
        str += "<script src=\"../src/layui.js\"></script>";
        str += "<script>";
        str += "    layui.use('element', function(){";
        str += "        var element = layui.element;";
        str += "        var $ = layui.$;";
        str += "        $(\"[name=t1]\").click(function () {";
        str += "            var id = $(this).attr(\"id\");";
        str += "            var content = $(this).attr(\"content\");";
        str += "            if($(\"li[lay-id=\"+id+\"]\").length==0){";
        str += "                element.tabAdd(\"empTab\",{";
        str += "                    title:$(this).attr(\"title\"),";
        str += "                    content:\"<iframe src='\"+content+\"' class='frame' frameborder='0'></iframe>\",";
        str += "                    id:id";
        str += "                })";
        str += "            }";
        str += "            element.tabChange('empTab',id)";
        str += "        })";
        str += "";
        str += "    });";
        str += "</script>";
        str += "</body>";
        str += "</body>";
        str += "</html>";

        return str;
    }

    public static String buildTableHTMLCode(AdminTable table) {
        String str = "";
        str += "<!DOCTYPE html>";
        str += "<html lang=\"en\">";
        str += "<head>";
        str += "    <meta charset=\"UTF-8\">";
        str += "    <title>" + table.getTableName() + "</title>";
        str += "    <link rel=\"stylesheet\" href=\"layui/css/layui.css\">";
        str += "    <script src=\"layui/layui.js\"></script>";
        str += "</head>";
        str += "<body>";
        str += "<div class=\"layui-form\">";
        str += "    <!-- 此处可以编写个性化搜索 -->";
        str += "    <lable class=\"layui-form-label\">" + table.getProperties().get(0)[0] + "</lable>";
        str += "    <div class=\"layui-inline\">";
        str += "        <input class=\"layui-input\" id=\"searchName\" type=\"text\">";
        str += "    </div>";
        str += "    <button type=\"button\" class=\"layui-btn\" id=\"searchAnything\">";
        str += "        <i class=\"layui-icon\">&#xe615;</i>";
        str += "    </button>";
        str += "</div>";
        str += "";
        str += "<!-- 凡是看见department都用htmlId替换 -->";
        str += "<table id=\"" + table.getHtmlId() + "\" lay-filter=\"" + table.getHtmlId() + "Table\">";
        str += "";
        str += "</table>";
        str += "";
        str += "<!---------------------添加科室表格 start------------------------------->";
        str += "<form class=\"layui-form\" id=\"add" + table.getHtmlId().substring(0,1).toUpperCase()+table.getHtmlId().substring(1) + "\" style=\"display: none;padding: 20px 30px 0 0\" lay-filter=\"insertFilter\">";
        for (int i = 0; i < table.getProperties().size(); i++) {
            str += "    <div class=\"layui-form-item\">";
            str += "        <label class=\"layui-form-label\">" + table.getProperties().get(i)[1] + "</label>";
            str += "        <div class=\"layui-input-inline\">";
            str += "            <input type=\"text\" name=\"" + table.getProperties().get(i)[1] + "\" placeholder=\"请输入" + table.getProperties().get(i)[1] + "\" autocomplete=\"off\" class=\"layui-input\">";
            str += "        </div>";
            str += "    </div>";
        }
        str += "    <div class=\"layui-form-item\">";
        str += "        <div class=\"layui-input-block\">";
        str += "            <button class=\"layui-btn\" lay-submit lay-filter=\"addSubmit\">立即提交</button>";
        str += "            <button type=\"reset\" class=\"layui-btn layui-btn-primary\">重置</button>";
        str += "        </div>";
        str += "    </div>";
        str += "</form>";
        str += "<!---------------------添加文章表格 end------------------------------->";
        str += "<!---------------------修改文章表格 start------------------------------->";
        str += "<form class=\"layui-form\" id=\"update" + table.getHtmlId().substring(0,1).toUpperCase()+table.getHtmlId().substring(1) + "\" style=\"display: none;padding: 20px 30px 0 0\" lay-filter=\"updateFilter\">";
        for (int i = 0; i < table.getProperties().size(); i++) {
            str += "    <div class=\"layui-form-item\">";
            str += "        <label class=\"layui-form-label\">" + table.getProperties().get(i)[1] + "</label>";
            str += "        <div class=\"layui-input-inline\">";
            str += "            <input type=\"text\" name=\"" + table.getProperties().get(i)[1] + "\" placeholder=\"请输入" + table.getProperties().get(i)[1] + "\" autocomplete=\"off\" class=\"layui-input\">";
            str += "        </div>";
            str += "    </div>";
        }
        str += "    <div class=\"layui-form-item\">";
        str += "        <div class=\"layui-input-block\">";
        str += "            <button class=\"layui-btn\" lay-submit lay-filter=\"updateSubmit\">立即提交</button>";
        str += "            <button type=\"reset\" class=\"layui-btn layui-btn-primary\">重置</button>";
        str += "        </div>";
        str += "    </div>";
        str += "</form>";
        str += "<!---------------------修改文章表格 end------------------------------->";
        str += "";


        str += "<!--===============添加、批量删除工具条 start===================-->";
        str += "<script type=\"text/html\" id=\"toolbar2\">";
        str += "    <a class=\"layui-btn layui-btn-sm\" lay-event=\"add\">添加</a>";
        if (table.isDeleteEnable()) {
            str += "    <a class=\"layui-btn layui-btn-sm layui-btn-danger\" lay-event=\"deleteMany\">批量删除</a>";
        }
        str += "</script>";
        str += "<!--===============添加、批量删除工具条 end  ===================-->";

        if (table.getSubTables() != null) {
            for (int i = 0; i < table.getSubTables().size(); i++) {
                str += "<script type=\"text/html\" id=\"" + table.getSubTables().get(i).getHtmlId() + "\">";
                str += "    <a class=\"layui-btn layui-btn-xs layui-btn-normal\" lay-event=\"goto" + table.getSubTables().get(i).getHtmlId().substring(0,1).toUpperCase() + table.getSubTables().get(i).getHtmlId().substring(1) + "\">详情</a>";
                str += "</script>";
            }
        }

        if (table.getOperations() != null) {
            str += "<script type=\"text/html\" id=\"" + "operations" + "\">";
            for (int i = 0; i < table.getOperations().size(); i++) {
                str += "    <a class=\"layui-btn layui-btn-xs layui-btn-normal\" lay-event=\"" + table.getOperations().get(i)[0] + "\">" + table.getOperations().get(i)[1] + "</a>";
            }
            str += "</script>";
        }

        str += "<!--===============删除、修改工具条 start===================-->";
        str += "<script type=\"text/html\" id=\"toolbar1\">";
        if (table.isEditEnable()) {
            str += "    <a class=\"layui-btn layui-btn-xs\" lay-event=\"update\">修改</a>";
        }
        if (table.isDeleteEnable()) {
            str += "    <a class=\"layui-btn layui-btn-xs layui-btn-danger\" lay-event=\"delete\">删除</a>";
        }
        str += "</script>";
        str += "<!--===============删除、修改工具条 end   ===================-->";


        str += "<script>";
        str += "    layui.use([\"form\",\"layer\",\"table\"],function () {";
        if (table.isSub()) {
            str += "var loc=location.href;";
            str += "var n1=loc.length;";
            str += "var n2=loc.indexOf(\"=\");";
            str += "var id=decodeURI(loc.substr(n2+1, n1-n2));";
        }
        str += "        var form = layui.form;";
        str += "        var layer = layui.layer;";
        str += "        var table = layui.table;";
        str += "        var $ = layui.$;";
        str += "";
        str += "        table.render({";
        str += "            elem:\"#" + table.getHtmlId() + "\",";
        str += "            limit:5,";
        str += "            limits:[5,10,15],";
        if (table.isSub()) {
            str += "            url:\"" + table.getHtmlId() + "/select.do?id=\" + id,";
        } else {
            str += "            url:\"" + table.getHtmlId() + "/select.do\",";
        }
        str += "            page:true,";
        str += "            toolbar:\"#toolbar2\",";
        str += "            cols:[[";
        str += "                {type:\"checkbox\"},";
        for (int i = 0; i < table.getProperties().size(); i++) {
            str += "                {field:\"" + table.getProperties().get(i)[0] + "\",title:\"" + table.getProperties().get(i)[1] + "\"},";
        }

        if (table.getSubTables() != null) {
            for (int i = 0; i < table.getSubTables().size(); i++) {
                str += "                {title:\"" + table.getSubTables().get(i).getTableName() + "\",toolbar:'#" + table.getSubTables().get(i).getHtmlId() + "'},";
            }
        }
        if (table.getOperations() != null) {
            str += "                {title:\"操作\",toolbar:'#operations'},";
        }
        str += "                {title:\"修改/删除\",toolbar:'#toolbar1'}";
        str += "            ]]";
        str += "        });";
        str += "";
        str += "        $(\"#searchAnything\").click(function () {";
        str += "";
        str += "            table.reload('" + table.getHtmlId() + "',{";
        str += "                where:{";
        str += "                    " + "id"+ ":$(\"#searchName\").val(),";
        str += "                },";
        str += "                page:{";
        str += "                    curr:1";
        str += "                }";
        str += "            })";
        str += "        });";
        str += "";
        str += "        table.on('toolbar(" + table.getHtmlId() + "Table)',function (obj) {";
        str += "            var checkStatus = table.checkStatus(obj.config.id);";
        str += "";
        str += "            console.log(obj.config.id);";
        str += "            if(obj.event =='add'){";
        str += "";
        str += "                layer.open({";
        str += "                    type:1,";
        str += "                    content:$(\"#add" + table.getHtmlId().substring(0,1).toUpperCase()+table.getHtmlId().substring(1) + "\"),";
        str += "                    area:[\"400px\",\"350px\"],";
        str += "                    title:'添加" + table.getTableName().substring(0,table.getTableName().length()-2) + "'";
        str += "                })";
        str += "            }else if(obj.event == 'deleteMany'){";
        str += "                var array = checkStatus.data;";
        str += "                if(array.length == 0){";
        str += "                    layer.msg(\"请至少选择一个复选框\",{icon:5,time:2000});";
        str += "                    return;";
        str += "                }";
        str += "                layer.confirm(\"检查一下别错删哦！\",{icon:6},function () {";
        str += "                    var ids = [];";
        str += "                    for(var i=0;i<array.length;i++){";
        str += "                        ids.push(array[i].id);";
        str += "                    }";
        str += "                    $.ajax({";
        str += "                        url:\"" + table.getHtmlId() + "/deleteMany.do\",";
        str += "                        data:\"ids=\"+ids,";
        str += "                        dataType:\"json\",";
        str += "                        success:function (data) {";
        str += "                            if(data.isDelete){";
        str += "                                layer.msg(\"删除成功\",{icon:6,time:2000});";
        str += "                                table.reload('" + table.getHtmlId() + "')";
        str += "                            }else{";
        str += "                                layer.msg(\"删除失败\",{icon:5,time:2000});";
        str += "                            }";
        str += "                        }";
        str += "                    })";
        str += "                })";
        str += "";
        str += "            }";
        str += "        });";
        str += "        table.on('tool(" + table.getHtmlId() + "Table)',function (obj) {";
        str += "            if(obj.event =='update'){";
        str += "";
        str += "                $.ajax({";
        str += "                    url:\"" + table.getHtmlId() + "/selectOne.do\",";
        str += "                    data:\"id=\"+obj.data.id,";
        str += "                    dataType:\"json\",";
        str += "                    success:function (data) {";
        str += "                        form.val('updateFilter',{";
        str += "                            id:data.id,";
        str += "                            name:data.name,";
        str += "                            telephone:data.telephone,";
        str += "                            area:data.area,";
        str += "                        })";
        str += "                    }";
        str += "                });";
        str += "                layer.open({";
        str += "                    type:1,";
        str += "                    content:$(\"#update" + table.getHtmlId().substring(0,1).toUpperCase()+table.getHtmlId().substring(1) + "\"),";
        str += "                    area:[\"400px\",\"350px\"],";
        str += "                    title:'修改'";
        str += "                })";
        str += "            }else if(obj.event == 'delete'){";
        str += "                layer.confirm(\"确定要删除吗？\",{icon:5},function (index) {";
        str += "                    var id = obj.data.id;";
        str += "                    $.ajax({";
        str += "                        url:\"" + table.getHtmlId() + "/delete.do\",";
        str += "                        dataType:\"json\",";
        str += "                        data:\"id=\"+id,";
        str += "                        success:function (data) {";
        str += "                            if(data.isDelete){";
        str += "                                layer.msg(\"删除成功\",{icon:1,time:2000});";
        str += "                                table.reload('" + table.getHtmlId() + "');";
        str += "                            }";
        str += "                        }";
        str += "                    })";
        str += "                })";
        str += "            }";
        if (table.getSubTables() != null) {
            for (int i = 0; i < table.getSubTables().size(); i++) {
                str += "if (obj.event == 'goto" + table.getSubTables().get(i).getHtmlId().substring(0,1).toUpperCase() + table.getSubTables().get(i).getHtmlId().substring(1) + "') {";
                str += "location.href='" + table.getSubTables().get(i).getTableName() + ".html?'+'id=' + encodeURI(obj.data.id);";
                str += "        }";
            }
        }
        if (table.getOperations() != null) {
            for (int i = 0; i < table.getOperations().size(); i++) {
                str += "if (obj.event == '" + table.getOperations().get(i)[0] + "') {";
                str += "            console.log(obj.data.id + \"" + "    " + table.getOperations().get(i)[0] + "\")";
                str += "        }";
            }
        }
        str += "        });";
        str += "        form.on('submit(addSubmit)',function () {";
        str += "";
        str += "            $.ajax({";
        str += "                type:\"post\",";
        str += "                url:\"" + table.getHtmlId() + "/insert.do\",";
        str += "                data:$(\"#add" + table.getHtmlId().substring(0,1).toUpperCase()+table.getHtmlId().substring(1) + "\").serialize(),";
        str += "                success:function (data) {";
        str += "                    if(data.isAdd){";
        str += "                        /*layer.msg(\"图书添加成功\", {icon: 1,time:3000});*/";
        str += "                        $(\"#add" + table.getHtmlId().substring(0,1).toUpperCase()+table.getHtmlId().substring(1) + "\")[0].reset();";
        str += "                        layer.confirm(\"添加成功,要退出添加界面吗？\",{icon: 1},function (index) {";
        str += "                            layer.closeAll();";
        str += "                        });";
        str += "                        table.reload('" + table.getHtmlId() + "')";
        str += "                    }";
        str += "                },";
        str += "                dateType:\"json\"";
        str += "            });";
        str += "            return false;";
        str += "        });";
        str += "        form.on('submit(updateSubmit)',function () {";
        str += "            $.ajax({";
        str += "                type:\"post\",";
        str += "                url:\"" + table.getHtmlId() + "/update.do\",";
        str += "                data:$(\"#update" + table.getHtmlId().substring(0,1).toUpperCase()+table.getHtmlId().substring(1) + "\").serialize(),";
        str += "                success:function (data) {";
        str += "                    console.log(data);";
        str += "                    if(data.isUpdate){";
        str += "                        layer.confirm(\"修改成功,要退出修改界面吗？\",{icon: 1},function (index) {";
        str += "                            layer.closeAll();";
        str += "                        });";
        str += "                        table.reload('" + table.getHtmlId() + "')";
        str += "                    }";
        str += "                },";
        str += "                dateType:\"json\"";
        str += "            });";
        str += "            return false;";
        str += "        });";
        str += "";
        str += "    })";
        str += "</script>";
        str += "</body>";
        str += "</html>";

        return str;
    }
}
