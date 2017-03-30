package com.anchorren.image.process;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import com.anchorren.image.util.DBCPUtil;
import com.anchorren.image.util.ImageUtil;
import com.anchorren.image.util.PictureProcess;

/**
 * 提取图像的颜色特征矩阵，存储都数据库中。
 * @author REN
 * @time:2017年3月27日 下午2:56:25
 */
public class ColorFeatureExport {

	public static void main(String[] args) {
		//1 读取本地图片库中图像
		String  PATH = "F:\\snaps\\images\\wms";
		List<String> dirList = PictureProcess.getDirectoryList(PATH);
		//System.out.println(dirList);
		System.out.println("NUM : " + dirList.size());
		for (String dir : dirList) {
		//2. 在每个文件夹下寻找 Id_1.png Id_2.png Id_3.png的图片	
			System.out.println(dir.split("\\\\")[4]);
			int id = Integer.parseInt(dir.split("\\\\")[4]);
			String path1 = dir + "\\" + dir.split("\\\\")[4] + "_1.png";
			//String path2 = dir + "\\" + dir.split("\\")[4] + "_2.png";
			//String path3 = dir + "\\" + dir.split("\\")[4] + "_3.png";
			
			if(new File(path1).exists()){
				//3.计算颜色直方图
				int[] hist;
				try {
					hist = ImageUtil.getImageHSVHist(path1);
					if(hist[2] == 65536){
						continue;
					}
				} catch (Exception e) {
					continue;
				}
				System.out.println("ID : " + id);
				System.out.println("文件路径 : " + path1);
				System.out.println("颜色直方图 ： " + Arrays.toString(hist));
				Connection connection = null; 
				PreparedStatement stat = null;
				try {
					connection = DBCPUtil.getConnection();
					stat = connection.prepareStatement("INSERT into image_feature (image_id,color) values (?, ?)");
					stat.setInt(1, id);
					stat.setString(2, Arrays.toString(hist));
					boolean result = stat.execute();
					System.out.println("数据插入结果 ： " + (!result ? "成功！" : "失败"));
					System.out.println("====================================================");
				} catch (SQLException e) {
					e.printStackTrace();
				}finally{
					DBCPUtil.release(stat, connection);
				}
			}
		}
	}
	
	
}
