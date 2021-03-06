﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using linqToDataSet;

namespace Wpf
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
        }

        private void Window_Loaded(object sender, RoutedEventArgs e)
        {
            //TODO: bind hierarchical data with infinite level of nesting
            // http://social.msdn.microsoft.com/forums/en-US/wpf/thread/3a319a26-a910-47ef-88eb-97af09e29082/
            // http://msdn.microsoft.com/en-us/library/system.windows.hierarchicaldatatemplate(vs.85).aspx

            ShowTree();
        }

        private void ShowTree()
        {
            var ds = new CompanyDataSet();
            var cda = new linqToDataSet.CompanyDataSetTableAdapters.CompanyTableAdapter();
            cda.Fill(ds.Company);

            var tvCompanyItem = new TreeViewItem
            {
                Header = ds.Company.FirstOrDefault().Name
            };
            this.treeCompany.Items.Add(tvCompanyItem);
            
            var dda = new linqToDataSet.CompanyDataSetTableAdapters.DepartmentTableAdapter();
            dda.Fill(ds.Department);

            var eda = new linqToDataSet.CompanyDataSetTableAdapters.EmployeeTableAdapter();
            eda.Fill(ds.Employee);

            var pda = new linqToDataSet.CompanyDataSetTableAdapters.PersonTableAdapter();
            pda.Fill(ds.Person);

            foreach (var dept in ds.Department.Where(d=>d.Isparent_idNull()))
            {
                var tv = new TreeViewItem
                {
                    Header = dept.Name,
                    Tag = new DepartmentDetailsPage(dept.department_id)
                };
                tvCompanyItem.Items.Add(tv);
                GetSubTree(ref tv, dept);
            }
        }

        private void GetSubTree(ref TreeViewItem iSubTree, CompanyDataSet.DepartmentRow department)
        {
            var employees = department.GetEmployeeRows();
            
            foreach (var employee in employees)
            {
                var ep = new EmployeeDetailsPage(employee.employee_id);
                ep.OnEmployeeChanged += (s, e) => ((TreeViewItem) treeCompany.SelectedItem).Header = e.Name;

                var subItem = new TreeViewItem
                {
                    Header = employee.PersonRow.Name,
                    Tag = ep
                };
                iSubTree.Items.Add(subItem);
            }

            foreach (var subDept in department.GetDepartmentRows())
            {
                var subItem = new TreeViewItem
                {
                    Header = subDept.Name,
                    Tag = new DepartmentDetailsPage(subDept.department_id)
                };
                iSubTree.Items.Add(subItem);
                GetSubTree(ref subItem, subDept);
            }
        }

        private void TreeCompanySelectedItemChanged(object sender, RoutedPropertyChangedEventArgs<object> e)
        {
            var page = ((TreeViewItem)e.NewValue).Tag as Page;
            
            if (page == null)
            {
                page = new Home();
            }
            
            this.ContentFrame.NavigationService.Navigate(page);
        }
    }
}
