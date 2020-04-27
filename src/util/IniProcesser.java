package util;

import java.io.BufferedReader;

import java.io.BufferedWriter;

import java.io.File;

import java.io.FileReader;

import java.io.FileWriter;

import java.io.PrintWriter;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.Iterator;

import java.util.Map;

/**
 * Ini����
 * <p>
 * ��*.ini�ļ����ж�ȡ/д��
 */
public class IniProcesser {

	/**
	 * 
	 * ��ȡINI����
	 * 
	 * @param file
	 *            INI�����ļ�����·��
	 * 
	 * @param sec
	 *            ��
	 * 
	 * @param key
	 *            ��
	 * 
	 * @param defaults
	 *            Ĭ��ֵ
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String GetPrivateProfileString(String file, String sec,
			String key, String defaults)

	{

		String result = defaults;

		Map map = getIniAllValue(file);

		if (map == null)

			return result;

		ArrayList section = (ArrayList) map.get(sec);

		if (section != null)

		{

			Iterator iter = section.iterator();

			while (iter.hasNext()) {

				String[] kv = (String[]) iter.next();

				if (kv != null && kv[0].equals(key.trim())) {

					return dealCorpsSign(kv[1], 2);

				}

			}

		}

		System.out.println("return defaults:"+defaults);
		return defaults;

	}

	/**
	 * 
	 * д������ INI�ļ�
	 * 
	 * @param file
	 *            INI�����ļ�����·��
	 * 
	 * @param sec
	 *            ��
	 * 
	 * @param key
	 *            ��
	 * 
	 * @param value
	 *            ֵ
	 * 
	 * @return
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean WritePrivateProfileString(String file, String sec,
			String key, String value) throws Exception

	{

		value = dealCorpsSign(value, 1);

		Map map = getIniAllValue(file);

		if (map == null)

		{

			map = new HashMap();

			ArrayList section = new ArrayList();

			section.add(new String[] { key, value });

			map.put(sec, section);

		}

		else {

			int x = 0, y = 0;

			ArrayList al = (ArrayList) map.get(sec);

			if (al != null) {

				Iterator iter = al.iterator();

				while (iter.hasNext()) {

					x++;

					String[] kv = (String[]) iter.next();

					if (kv != null && kv[0].equals(key)) {

						kv[1] = value;

						y++;

					}

				}

			}

			if (x == 0) {

				ArrayList section = new ArrayList();

				section.add(new String[] { key, value });

				map.put(sec, section);

			}

			else if (y == 0) {

				al.add(new String[] { key, value });

				map.put(sec, al);

			}

		}

		// д���ļ�

		try {

			PrintWriter out = new PrintWriter(new BufferedWriter(
					new FileWriter(file)));

			Iterator iter = map.keySet().iterator();

			while (iter.hasNext())

			{

				Object obj = iter.next();

				out.println(System.getProperty("line.separator") + "[" + obj
						+ "]");

				ArrayList aList = (ArrayList) map.get(obj);

				if (aList != null) {

					Iterator res = aList.iterator();

					while (res.hasNext()) {

						String[] kv = (String[]) res.next();

						out.println(kv[0] + "=" + kv[1]);

					}

				}

			}

			out.close();

		} catch (Exception e) {

			return false;

		}

		return true;

	}

	private static String dealCorpsSign(String str, int flag)

	{

		String xline = System.getProperty("line.separator");

		if (flag == 1)

			str = str.replace(xline, "��41��3��");

		else

			str = str.replace("��41��3��", xline);

		return str;

	}

	/**
	 * 
	 * ��ȡ��������
	 * 
	 * @param file
	 * 
	 * @return
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Map getIniAllValue(String file)

	{

		Map map = new HashMap();

		try {

			File f = new File(file);

			if (!f.exists())

				return null;

			BufferedReader in = new BufferedReader(new FileReader(file));

			String line = null;

			ArrayList values = null;

			while ((line = in.readLine()) != null)

			{

				if (isSection(line)) {

					// ��ȡ��

					values = new ArrayList();

					map.put(line.substring(1, line.length() - 1), values);

				} else if (values != null) {

					// ��ȡ������ļ���ֵ

					int index = line.indexOf("=");

					if (index > 0) {

						String k = line.substring(0, index).trim();

						String note = k.substring(0, 1);

						if (note.equals("#") || note.equals("/")
								|| note.equals(";"))

							continue;

						String v = line.substring(index + 1).trim();

						String[] kv = { k, v };

						values.add(kv);

					}

				}

			}

			in.close();

			return map;

		}

		catch (Exception e) {

			return null;

		}

	}

	/**
	 * 
	 * �ж��Ƿ�Ϊ����
	 * 
	 * @param str
	 * 
	 * @return
	 */

	private static boolean isSection(String str)

	{

		boolean result = false;

		if (str != null && str.startsWith("[") && str.endsWith("]")) {

			result = true;

		}

		return result;

	}

}
