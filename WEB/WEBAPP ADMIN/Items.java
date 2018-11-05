
package com.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.database.AdminDAO;


@SuppressWarnings("serial")
public class Items extends HttpServlet 
{
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws IOException
	{
		PrintWriter out = response.getWriter();
		ResultSet rs=null; 
		RequestDispatcher rd=null;
		boolean flag = false;
		int routeId=0;
		String name="",cat="",item="",price="",phone="",mail="",sex="";
		int companycell11=0;
		int landline=0;
		int cmpnycode=0;
		int userid=0;
		
		try
		{
			
			String submit=request.getParameter("action");
			
			System.out.println("submit value>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+submit);
			
			rs=AdminDAO.getitemsdetails();
			
			if(submit.equals("get"))
			{
				request.setAttribute("rs", rs);
				rd=request.getRequestDispatcher("/jsp/items.jsp");
				rd.forward(request, response);
			}
			
			if(submit.equals("Add"))
			{
				if(Utility.parse1(request.getParameter("add")).equals("YES"))
				{
					
					System.out.println("adddddddddd");
					cat = request.getParameter("cat");
					
					
					item = request.getParameter("item");
					
					
					price = request.getParameter("price");
					
					
					
					
					
				
				 flag = AdminDAO.checkitemExistance(item,cat);
					
					if(!flag)
					{
						flag = AdminDAO.additemDetails(cat, item, price);
						
						
						if(flag)
						{
							rs=AdminDAO.getitemsdetails();
							request.setAttribute("rs", rs);
							rd=request.getRequestDispatcher("/jsp/items.jsp?no=1");
							rd.forward(request, response);
						}
						else
						{
							rd=request.getRequestDispatcher("/jsp/add_items.jsp?no=2");
							rd.forward(request, response);
						}
							 
					}
					else
					{
						rd=request.getRequestDispatcher("/jsp/add_items.jsp?no=3");
						rd.forward(request, response);
					}
					
				}
				else
				{
					rd=request.getRequestDispatcher("/jsp/add_items.jsp");
					rd.forward(request, response);
				}
			}
			
			
	if(submit.equals("Edit"))
			{
			
			System.out.println("submit in edit>>>>>>>>>>>>>>>"+submit );
				String []chk=request.getParameterValues("chk");
				if(Utility.parse1(request.getParameter("edit")).equals("YES"))
				{
					System.out.println("its came inside if block");
					String idd =request.getParameter("id");
					String itemm =request.getParameter("item");
				  String	pricee = request.getParameter("price");
				  String	cidd = request.getParameter("cid");
				  
					
					ArrayList<String> list = new ArrayList<String>();
					list.add(idd);
					list.add(cidd);
					list.add(itemm);
					list.add(pricee);
					
					flag = AdminDAO.updateitemsDetails(list);
					
					if(flag)
					{
						rs=AdminDAO.getitemsdetails();
						request.setAttribute("rs", rs);
						rd=request.getRequestDispatcher("/jsp/items.jsp?no=5");
						rd.forward(request,response);
					}
					else
					{
						rs=AdminDAO.getitemsdetails(chk[0]);
						request.setAttribute("rs", rs);
						rd=request.getRequestDispatcher("/jsp/edit_items.jsp?no=2");
						rd.forward(request,response);
					}
					
				}
				else if(chk==null)
				{
					rs=AdminDAO.getitemsdetails();
					request.setAttribute("rs", rs);
					rd=request.getRequestDispatcher("/jsp/items.jsp?no=2");
					rd.forward(request,response);
				}
				else if(chk.length!=1)
				{
					rs=AdminDAO.getitemsdetails();
					request.setAttribute("rs", rs);
					rd=request.getRequestDispatcher("/jsp/items.jsp?no=4");
					rd.forward(request,response);
				}
				else if(chk.length==1)
				{
					rs=AdminDAO.getitemsdetails(chk[0]);
					request.setAttribute("rs", rs);
					rd=request.getRequestDispatcher("/jsp/edit_items.jsp");
					rd.forward(request,response);
				}
			}

			
		
		if(submit.equals("Delete"))
			{
				String []chk=request.getParameterValues("chk");
				System.out.println("its came inside delete>>>>>>>>>>>>>>>>");
			
					//Without Delete Confirmation
					if(chk==null)
					{
						rs=AdminDAO.getitemsdetails();
						request.setAttribute("rs", rs);
						rd=request.getRequestDispatcher("/jsp/items.jsp?no=2");
						rd.forward(request,response);
					}
					else
					{
						
					
				
				
				//With Delete Confirmation
				
				for(int i=0;i<chk.length;i++)
				{
					String id = chk[i];
					AdminDAO.deleteitemDetails(id);
					
				}
				rs=AdminDAO.getitemsdetails();
				request.setAttribute("rs", rs);
				rd=request.getRequestDispatcher("/jsp/items.jsp?no=3");
				rd.forward(request,response);
			}
		
			}
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
			
		}
	
}}
