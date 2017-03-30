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
 * ��ȡͼ�����ɫ�������󣬴洢�����ݿ��С�
 * @author REN
 * @time:2017��3��27�� ����2:56:25
 */
public class ColorFeatureExport {

	public static void main(String[] args) {
		//1 ��ȡ����ͼƬ����ͼ��
		String  PATH = "F:\\snaps\\images\\wms";
		List<String> dirList = PictureProcess.getDirectoryList(PATH);
		//System.out.println(dirList);
		System.out.println("NUM : " + dirList.size());
		for (String dir : dirList) {
		//2. ��ÿ���ļ�����Ѱ�� Id_1.png Id_2.png Id_3.png��ͼƬ	
			System.out.println(dir.split("\\\\")[4]);
			int id = Integer.parseInt(dir.split("\\\\")[4]);
			String path1 = dir + "\\" + dir.split("\\\\")[4] + "_1.png";
			//String path2 = dir + "\\" + dir.split("\\")[4] + "_2.png";
			//String path3 = dir + "\\" + dir.split("\\")[4] + "_3.png";
			
			if(new File(path1).exists()){
				//3.������ɫֱ��ͼ
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
				System.out.println("�ļ�·�� : " + path1);
				System.out.println("��ɫֱ��ͼ �� " + Arrays.toString(hist));
				Connection connection = null; 
				PreparedStatement stat = null;
				try {
					connection = DBCPUtil.getConnection();
					stat = connection.prepareStatement("INSERT into image_feature (image_id,color) values (?, ?)");
					stat.setInt(1, id);
					stat.setString(2, Arrays.toString(hist));
					boolean result = stat.execute();
					System.out.println("���ݲ����� �� " + (!result ? "�ɹ���" : "ʧ��"));
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
