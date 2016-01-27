package download;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by daimajia on 14-1-30.
 */
public class AlfredUtils {
    public static boolean isFilenameValid(String file) {
        File f = new File(file);
        try {

/*
file的getPath getAbsolutePath和getCanonicalPath的不同

file的这几个取得path的方法各有不同，下边说说详细的区别

概念上的区别：（内容来自jdk，个人感觉这个描述信息，只能让明白的人明白，不明白的人看起来还是有点难度（特别试中文版，英文版稍好些)所以在概念之后我会举例说明。如果感觉看概念很累就跳过直接看例子吧。看完例子回来看概念会好些。
getPath
public String getPath()将此抽象路径名转换为一个路径名字符串。所得到的字符串使用默认名称分隔符来分隔名称序列中的名称。 

返回：
此抽象路径名的字符串形式


getAbsolutePath
public String getAbsolutePath()返回抽象路径名的绝对路径名字符串。 
如果此抽象路径名已经是绝对路径名，则返回该路径名字符串，这与 getPath() 方法一样。如果此抽象路径名是空的抽象路径名，则返回当前用户目录的路径名字符串，该目录由系统属性 user.dir 指定。否则，使用与系统有关的方式分析此路径名。在 UNIX 系统上，通过根据当前用户目录分析某一相对路径名，可使该路径名成为绝对路径名。在 Microsoft Windows 系统上，通过由路径名指定的当前驱动器目录（如果有）来分析某一相对路径名，可使该路径名成为绝对路径名；否则，可以根据当前用户目录来分析它。 


返回：
绝对路径名字符串，它与此抽象路径名表示相同的文件或目录的 
抛出： 
SecurityException - 如果无法访问所需的系统属性值。
另请参见：
isAbsolute()


getCanonicalPath
public String getCanonicalPath()
                        throws IOException返回抽象路径名的规范路径名字符串。 
规范路径名是绝对路径名，并且是惟一的。规范路径名的准确定义与系统有关。如有必要，此方法首先将路径名转换成绝对路径名，这与调用 getAbsolutePath() 方法的效果一样，然后用与系统相关的方式将它映射到其惟一路径名。这通常涉及到从路径名中移除多余的名称（比如 "." 和 ".."）、分析符号连接（对于 UNIX 平台），以及将驱动器名转换成标准大小写形式（对于 Microsoft Windows 平台）。 

表示现有文件或目录的每个路径名都有一个惟一的规范形式。表示非存在文件或目录的每个路径名也有一个惟一的规范形式。非存在文件或目录路径名的规范形式可能不同于创建文件或目录之后同一路径名的规范形式。同样，现有文件或目录路径名的规范形式可能不同于删除文件或目录之后同一路径名的规范形式。 


返回：
表示与此抽象路径名相同的文件或目录的规范路径名字符串 
抛出： 
IOException - 如果发生 I/O 错误（可能是因为构造规范路径名需要进行文件系统查询） 
SecurityException - 如果无法访问所需的系统属性值，或者存在安全管理器，且其 SecurityManager.checkRead(java.io.FileDescriptor) 方法拒绝对该文件进行读取访问
从以下版本开始： 
JDK1.1 


二、例子：
1，getPath()与getAbsolutePath()的区别
public static void test1(){
        File file1 = new File(".\\test1.txt");
        File file2 = new File("D:\\workspace\\test\\test1.txt");
        System.out.println("-----默认相对路径：取得路径不同------");
        System.out.println(file1.getPath());
        System.out.println(file1.getAbsolutePath());
        System.out.println("-----默认绝对路径:取得路径相同------");
        System.out.println(file2.getPath());
        System.out.println(file2.getAbsolutePath());
        
    }

得到的结果：
-----默认相对路径：取得路径不同------
.\test1.txt
D:\workspace\test\.\test1.txt
-----默认绝对路径:取得路径相同------
D:\workspace\test\test1.txt
D:\workspace\test\test1.txt
因为getPath()得到的是构造file的时候的路径。
getAbsolutePath()得到的是全路径
如果构造的时候就是全路径那直接返回全路径
如果构造的时候试相对路径，返回当前目录的路径+构造file时候的路径

2，getAbsolutePath()和getCanonicalPath()的不同
public static void test2() throws Exception{
        File file = new File("..\\src\\test1.txt");
        System.out.println(file.getAbsolutePath());
        System.out.println(file.getCanonicalPath());
    }
得到的结果
D:\workspace\test\..\src\test1.txt
D:\workspace\src\test1.txt
可以看到CanonicalPath不但是全路径，而且把..或者.这样的符号解析出来。
3,getCanonicalPath()和自己的不同。
就是解释这段话:
表示现有文件或目录的每个路径名都有一个惟一的规范形式。表示非存在文件或目录的每个路径名也有一个惟一的规范形式。非存在文件或目录路径名的规范形式可能不同于创建文件或目录之后同一路径名的规范形式。同样，现有文件或目录路径名的规范形式可能不同于删除文件或目录之后同一路径名的规范形式。 

单下边这段代码是看不到结果的，要配合一定的操作来看。下边操作步骤，同时讲解

public static void test3() throws Exception{
        File file = new File("D:\\Text.txt");
        System.out.println(file.getCanonicalPath());
    }
步骤：
确定你的系统是Windows系统。
(1),确定D盘下没有Text.txt这个文件，直接执行这段代码，得到的结果是:
D:\Text.txt
注意这里试大写的Text.txt
(2)在D盘下建立一个文件，名叫text.txt，再次执行代码，得到结果
D:\text.txt
同样的代码得到不同的结果。
同时可以对比getAbsolutePath()看看，这个得到的结果是一样的。

原因：
window是大小写不敏感的，也就是说在windows上test.txt和Test.txt是一个文件，所以在windows上当文件不存在时，得到的路径就是按照输入的路径。但当文件存在时，就会按照实际的情况来显示。这也就是建立文件后和删除文件后会有不同的原因。文件夹和文件类似。

三、最后：
1，尝试在linux下执行上边的步骤，两次打印的结果是相同的，因为linux是大小写敏感的系统。
2，手动删掉test.txt,然后尝试执行下边代码
public static void test4() throws Exception{
        File file = new File("D:\\Text.txt");
        System.out.println(file.getCanonicalPath());
        File file1 = new File("D:\\text.txt");
        file1.createNewFile();
        file = new File("D:\\Text.txt");
        System.out.println(file.getCanonicalPath());
    }

public static void test3() throws Exception{
        File file1 = new File("D:\\text.txt");
        file1.createNewFile();
        File file = new File("D:\\Text.txt");
        System.out.println(file.getCanonicalPath());
    }
执行上边两个函数，看看结果，然后思考一下为什么？
1,的结果是两个大写，
2,的结果试两个小写
连续两个大写的，是否跟上边的矛盾 ？
这是因为虚拟机的缓存机制造成的。第一次File file = new File("D:\\Text.txt");决定了结果.

*/
            f.getCanonicalPath();
            return true;
        }
        catch (IOException e) {
            return false;
        }
    }

    public static String getReadableSize(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit)
            return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1)
                + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    public static String getReadableSize(long bytes) {
        if (bytes <= 0)
            return "0";
        final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(bytes) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(bytes
                / Math.pow(1024, digitGroups))
                + " " + units[digitGroups];
    }

    public static String getReadableSpeed(long downloaded,long timespend,TimeUnit timeUnit) {
        long span = timeUnit.toSeconds(timespend);
        if(timespend * span == 0){
            return "0";
        }
        return getReadableSize(downloaded/span,true) + "/s";
    }

    public static String getFileNameWithExtention(String url){
        String fileName;
        int slashIndex = url.lastIndexOf("/");
        int qIndex = url.lastIndexOf("?");
        if (qIndex > slashIndex) {//if has parameters
            fileName = url.substring(slashIndex + 1, qIndex);
        } else {
            fileName = url.substring(slashIndex + 1);
        }
        return fileName;
    }

    public static String getFileName(String url) {
        String fileNameWithExtension = getFileNameWithExtention(url);
        String fileName = fileNameWithExtension;
        if (fileNameWithExtension.contains(".")) {
            fileName = fileNameWithExtension.substring(0, fileNameWithExtension.lastIndexOf("."));
        }
        return fileName;
    }

    public static String getFileExtension(String url){
        String extenstion;
        int pointIndex = url.lastIndexOf(".");
        int qIndex = url.lastIndexOf("?");
        if(qIndex > pointIndex){
            extenstion = url.substring(pointIndex+1,qIndex);
        }else{
            extenstion = url.substring(pointIndex+1);
        }
        return extenstion;
    }

    public static int randInt(int min, int max) {

        // Usually this can be a field rather than a method variable
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}
