package com.anchorren.image.process;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import com.anchorren.image.util.DBCPUtil;
import com.anchorren.image.util.ImageUtil;
import com.anchorren.image.util.PictureProcess;
import com.mathworks.toolbox.javabuilder.MWException;

import Texture.TextureTool;

/**
 * ͼ������������ȡ
 * @author REN
 * @time:2017��3��29�� ����11:12:13
 */
public class TextureExport {

	public static void main(String[] args) {
		//1 ��ȡ����ͼƬ����ͼ��
		String  PATH = "F:\\snaps\\images\\arcgis";
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
			
			Connection con = null; 
			PreparedStatement state = null;
			try {
				con = DBCPUtil.getConnection();
				state = con.prepareStatement("SELECT * FROM image_feature WHERE image_id = ?");
				state.setInt(1, id);
				ResultSet res = state.executeQuery();
				if(!res.next()){
					continue;
				}
				//System.out.println(res.getString("texture"));
				if(res.getString("texture") != null && !res.getString("texture").equals("")){
					System.out.println("�Ѿ���ȡ������������");
					continue;
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}finally{
				DBCPUtil.release(state, con);
			}
			
			if(new File(path1).exists()){
				//3.������������
				TextureTool tool;
				Object[] texture ;
				try {
					tool = new TextureTool();
					texture = tool.Texture(1, path1);
				} catch (MWException e1) {
					continue;
				}
				 //System.out.println("length:" + texture.length);
				 //System.out.println(texture);
				 String[] split = texture[0].toString().split(" ");
				 String[] result = new String[8];
				 int i = 0;
				 for (String string : split) {
					 //System.out.println(string);
					 if(string.trim().length() >= 6){
						 //System.out.println(string.trim());
						 if(i == 7){
							 continue;
						 }
						 result[i++] = string.trim();
					 }
				}
				System.out.println("ID : " + id);
				System.out.println("�ļ�·�� : " + path1);
				System.out.println("�������� �� " + Arrays.toString(result));
				Connection connection = null; 
				PreparedStatement stat = null;
				try {
					connection = DBCPUtil.getConnection();
					stat = connection.prepareStatement("UPDATE image_feature SET texture = ? WHERE image_id = ?");
					stat.setString(1, Arrays.toString(result));
					stat.setInt(2, id);
					int res = stat.executeUpdate();
					System.out.println("���ݲ����� �� " + (res > 0 ? "�ɹ���" : "ʧ��"));
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
