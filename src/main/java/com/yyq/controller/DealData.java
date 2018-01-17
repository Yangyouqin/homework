package com.yyq.controller;

import com.yyq.domian.Message;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.Range;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/Plane")
public class DealData extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private String path = null;
    //消息提示
    private String message = null;

    private String mes = null;

    public String dealData(String path, int id) {
        File file = new File(path);
        boolean flag = false;
        List<Message> list = new ArrayList<Message>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            int i = 0;
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                String[] ss = s.trim().split("\\s+");
                flag = false;
                if (i == id) {
                    flag = true;
                    Message message = new Message();
                    message.setId(i);
                    message.setPlaneId(ss[0]);
                    message.setLastX(Integer.valueOf(ss[1]));
                    message.setLastY(Integer.valueOf(ss[2]));
                    message.setLastZ(Integer.valueOf(ss[3]));
                    if (ss.length == 10 && i != 0) {
                        message.setOffsetX(Integer.valueOf(ss[4]));
                        message.setOffsetY(Integer.valueOf(ss[5]));
                        message.setOffsetZ(Integer.valueOf(ss[6]));
                        message.setPresentX(Integer.valueOf(ss[7]));
                        message.setPresentY(Integer.valueOf(ss[8]));
                        message.setPresentZ(Integer.valueOf(ss[9]));
                        if (message.getLastX() + message.getOffsetX() == message.getPresentX() && message.getLastY() + message.getOffsetY() == message.getPresentY() && message.getLastZ() + message.getOffsetZ() == message.getPresentZ()) {
                            message.setState(true);
                            list.add(message);
                        } else {
                            return "Error："+i+"";
                        }
                    } else if (i != 0 && ss.length != 11) {
                        return "Error："+i+"";
                    }
                    if (flag == true && i == 0)
                        list.add(message);
                    break;
                }
                i++;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (flag == false) {
            return "Cannot find "+id+"";
        }  else {
            for (Message message : list) {
                return  message.getPlaneId() + "  " + message.getId() + "  " + message.getPresentX() + "  " + message.getPresentY() + "  " + message.getPresentZ()+"";
            }
        }
        return "";

    }

    public static String txt2String(File file) {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                result.append(s);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //得到上传文件的保存目录，将上传的文件存放于D目录下，不允许外界直接访问，保证上传文件的安全
        String savePath = "D:/upload";
        File file = new File(savePath);
        //判断上传文件的保存目录是否存在
        if (!file.exists() && !file.isDirectory()) {
            System.out.println(savePath + "目录不存在，需要创建");
            //创建目录
            file.mkdir();
        }
        try {
            //使用Apache文件上传组件处理文件上传步骤：
            //1、创建一个DiskFileItemFactory工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //2、创建一个文件上传解析器
            ServletFileUpload upload = new ServletFileUpload(factory);
            //解决上传文件名的中文乱码
            upload.setHeaderEncoding("UTF-8");
            //3、判断提交上来的数据是否是上传表单的数据
            if (!ServletFileUpload.isMultipartContent(request)) {
                //按照传统方式获取数据
                return;
            }
            //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
            List<FileItem> list = upload.parseRequest(request);
            for (FileItem item : list) {
                //如果fileitem中封装的是普通输入项的数据
                if (item.isFormField()) {
                    String name = item.getFieldName();
                    //解决普通输入项的数据的中文乱码问题
                    String value = item.getString("UTF-8");
                    //value = new String(value.getBytes("iso8859-1"),"UTF-8");
                    System.out.println(name + "=" + value);
                } else {//如果fileitem中封装的是上传文件
                    //得到上传的文件名称，
                    String filename = item.getName();
                    System.out.println(filename);
                    if (filename == null || filename.trim().equals("")) {
                        continue;
                    }
                    //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
                    //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
                    filename = filename.substring(filename.lastIndexOf("\\") + 1);
                    if(!filename.endsWith(".txt")){
                        message = "拒绝上传，请上传txt文件";
                        request.setAttribute("message", message);
                        request.getRequestDispatcher("/index.jsp").forward(request,response);
                        return;
                    }
                    //获取item中的上传文件的输入流
                    InputStream in = item.getInputStream();
                    //创建一个文件输出流
                    FileOutputStream out = new FileOutputStream(savePath + "\\" + filename);
                    //创建一个缓冲区
                    byte buffer[] = new byte[1024];
                    //判断输入流中的数据是否已经读完的标识
                    int len = 0;
                    //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
                    while ((len = in.read(buffer)) > 0) {
                        //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                        out.write(buffer, 0, len);
                    }
                    //关闭输入流
                    in.close();
                    //关闭输出流
                    out.close();
                    //删除处理文件上传时生成的临时文件
                    item.delete();
                    message = "文件上传成功！";
                    path = savePath + "/" + filename;
                }
            }
        } catch (Exception e) {
            message = "文件上传失败！";
            e.printStackTrace();

        }
        if(message!=null){
            request.setAttribute("message", message);
        }
        request.getRequestDispatcher("/index.jsp").forward(request,response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//        doGet(request, response);
        String id = request.getParameter("id");
        mes = dealData(path, Integer.valueOf(id));
        if(mes!=null){
            request.setAttribute("result", mes);
        }
        if(message!=null){
            request.setAttribute("message",message);
        }
        System.out.print(mes);

        request.getRequestDispatcher("/index.jsp").forward(request,response);

    }

}
