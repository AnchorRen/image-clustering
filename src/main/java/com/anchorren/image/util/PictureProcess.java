package com.anchorren.image.util;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * 图片处理工具类
 * @author REN
 * @time:2017年3月27日 下午3:09:27
 */
public class PictureProcess {

	private static final String PATH = "F:\\images2\\";

	public static void main(String[] args) throws FileNotFoundException, IOException {

		/*List<String> directoryList = getDirectoryList(PATH);
		for (String string : directoryList) {
			fileProcess(string);
			changeFileName(string);
		}*/
		
		String file = "E:\\images\\2205899\\2205899_01_01.png";
		System.out.println(file);
		boolean flag = deleteFile(file);
		System.out.println(flag);
	}
	public static void fileProcess(String path) {

		File file = new File(path);

		if (file.isDirectory()) {
			System.out.println("文件夹");
			String[] filelist = file.list();
			List<String> fileNameList = new ArrayList<String>();
			if (filelist.length > 0) {
				//判断文件夹中图片数量，如果图片数量小于等于4张。则全部作为缩略图。即不做处理
				if(filelist.length <= 4){
					return;
				}
				/*
				 * 找文件名中含有M的图片数量。如果大于等于4，则删除其他图片
				 */
				for (int i = 0; i < filelist.length; i++) {
					
					File readfile = new File(path + "\\" + filelist[i]);
					if (!readfile.isDirectory()) {// 是文件
						String fileName = readfile.getName();
						System.out.println("path=" + readfile.getPath());
						System.out.println("name=" + fileName);
						if(readfile.getName().contains("M")){
							String name = fileName.split("M")[0]+".png";
							File f = new File(path+"\\"+name);
							if(f.isFile() && f.exists()){
								fileNameList.add(name);
							}
							deleteFile(path+"\\"+fileName);//删除带M的图片
						}

					} 
				}
				
				filelist = file.list();//删除掉含有M的图片后。
				//如果缩略图等于三张或者四张，直接删除其他的所有图
			  if(fileNameList.size() >= 3){
				  
				  for (int i = 0; i < filelist.length; i++) {
						
						File readfile = new File(path + "\\" + filelist[i]);
						if (!readfile.isDirectory()) {// 是文件
							String fileName = readfile.getName();
							if(!fileNameList.contains(fileName)){
								deleteFile(path+"\\"+fileName);//删除
							}
						} 
					}
			  }
			  
			  /*//剩余图小于4，则不作处理了。
			  if(filelist.length <= 4){
				  return;
			  }else{
				  
			  }
			  
			  //如果所有图大于四张，但是fileNameList小于三张，则
*/			}
		}
	}

    /**
     * 删除单个文件
     * @param   sPath    被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
       boolean flag = false;
       File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
        	flag = file.delete();
        }
        return flag;
    }
	
	/**
	 * 把指定路徑下的文件按文件大小排序和重命名
	 * @param path
	 */
	public static void changeFileName(String path){
		
		File file = new File(path);
		Map<Long,String> map = new HashMap<Long, String>(); // 文件大小--文件名
		
		
		if (file.isDirectory()) {
			System.out.println("文件夹");
			String[] filelist = file.list();
			if (filelist.length > 0) {
				for (int i = 0; i < filelist.length; i++) {
					File readfile = new File(path + "\\" + filelist[i]);
					if (readfile.exists() && readfile.isFile()) {// 是文件
						String fileName = readfile.getName();
						System.out.println(fileName);
						Long length = getFileSize(readfile);
						System.out.println(length);
						if(map.get(length) != null){
							
							map.put(length+1, fileName);
						}else{
							map.put(length, fileName);
						}
					} 
				}
			}
		}
		
		System.out.println(map);
		
		Set<Long> set = map.keySet();
		Long[] array = new Long[set.size()];
		int i = 0;
		if(set.size() > 0){
			for (Long long1 : set) {
				array[i] = long1;
				i++;
			}
		}
		//Long[] array = (Long[]) set.toArray();
		System.out.println(Arrays.toString(array));
		array = bubbleSort(array);
		for (Long long1 : array) {
			System.out.println(long1+":"+map.get(long1));
		}
		
		for (int j =0;j<array.length; j++) {
			
			String fileName = map.get(array[j]);
			String id = fileName.split("_")[0];
			renameFile(path, fileName,id+"_"+(j+1)+".png" );
		}
	}
	
	/**
	 * 冒泡排序
	 * @param numbers
	 */
	public static Long[] bubbleSort(Long[] numbers) {   
	    Long temp; // 记录临时中间值   
	    int size = numbers.length; // 数组大小   
	    for (int i = 0; i < size - 1; i++) {   
	        for (int j = i + 1; j < size; j++) {   
	            if (numbers[i] < numbers[j]) { // 交换两数的位置   
	                temp = numbers[i];   
	                numbers[i] = numbers[j];   
	                numbers[j] = temp;   
	            }   
	        }   
	    }
		return numbers;  
	}
	    
		/**文件重命名 
	    * @param path 文件目录 
	    * @param oldname  原来的文件名 
	    * @param newname 新文件名 
	    */ 
	    public static  void renameFile(String path,String oldname,String newname){ 
	        if(!oldname.equals(newname)){//新的文件名和以前文件名不同时,才有必要进行重命名 
	            File oldfile=new File(path+"/"+oldname); 
	            File newfile=new File(path+"/"+newname); 
	            if(!oldfile.exists()){
	                return;//重命名文件不存在
	            }
	            if(newfile.exists())//若在该目录下已经有一个文件和新文件名相同，则不允许重命名 
	                System.out.println(newname+"已经存在！"); 
	            else{ 
	                oldfile.renameTo(newfile); 
	            } 
	        }else{
	            System.out.println("新文件名和旧文件名相同...");
	        }
	    }
	
	/**
	 * 计算指定文件的长度
	 * 
	 * @param path
	 *            文件路径
	 * @return 文件长度，如果文件不存在，则返回-1。
	 */
	public static Long getFileSize(File f) {
		
		if (f.exists() && f.isFile()) {
			return f.length();
		} else {
			System.out.println("file doesn't exist or is not a file");
			return -1L;
		}
	}
	
	/**
	 * 计算指定文件的长度
	 * 
	 * @param path
	 *            文件路径
	 * @return 文件长度，如果文件不存在，则返回-1。
	 */
	public static Long getFileSize(String path) {
		File f = new File(path);
		if (f.exists() && f.isFile()) {
			return f.length();
		} else {
			System.out.println("file doesn't exist or is not a file");
			return -1L;
		}
	}

	/**
	 * 获取 指定路径下的文件夹路径集合
	 * 
	 * @param filepath
	 * @return
	 */
	public static List<String> getDirectoryList(String filepath) {

		File file = new File(filepath);
		List<String> list = new ArrayList<String>();
		if (file.isDirectory()) {
			System.out.println("文件夹");
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				File readfile = new File(filepath + "\\" + filelist[i]);
				if (readfile.isDirectory()) {
					System.out.println("path=" + readfile.getPath());
					// System.out.println("absolutepath=" +
					// readfile.getAbsolutePath());
					// System.out.println("name=" + readfile.getName());
					System.out.println("----------------");
					list.add(readfile.getPath());
				}
			}
		}
		return list;
	}

	/**
	 * 读取某个文件夹下的所有文件。
	 * 
	 * @param filepath
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static boolean readfile(String filepath) throws FileNotFoundException, IOException {
		try {

			File file = new File(filepath);
			if (!file.isDirectory()) {
				System.out.println("文件");
				System.out.println("path=" + file.getPath());
				System.out.println("absolutepath=" + file.getAbsolutePath());
				System.out.println("name=" + file.getName());

			} else if (file.isDirectory()) {
				System.out.println("文件夹");
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File readfile = new File(filepath + "\\" + filelist[i]);
					if (!readfile.isDirectory()) {
						System.out.println("path=" + readfile.getPath());
						System.out.println("absolutepath=" + readfile.getAbsolutePath());
						System.out.println("name=" + readfile.getName());

					} else if (readfile.isDirectory()) {
						readfile(filepath + "\\" + filelist[i]);
					}
				}

			}

		} catch (FileNotFoundException e) {
			System.out.println("readfile()   Exception:" + e.getMessage());
		}
		return true;
	}
}
