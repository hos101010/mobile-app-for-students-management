//
//  ViewExam.swift
//  project2
//
//  Created by jina son on 28/10/2018.
//  Copyright © 2018 jina son. All rights reserved.
//

import UIKit
import Foundation

class ViewExam: UIViewController, UITableViewDelegate, UITableViewDataSource {
    

    @IBOutlet weak var exam_list_view: UITableView!
    @IBOutlet weak var notice_label: UILabel!
    
    var stu_id : Int = 0
    var exam_list_array:[String] = Array()
    let today = NSDate()
    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return exam_list_array.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        var tmp: String = ""
        let cell:UITableViewCell = exam_list_view.dequeueReusableCell(withIdentifier: "cell", for: indexPath)
        cell.textLabel!.text = exam_list_array[indexPath.row]
        tmp = cell.textLabel!.text!
        tmp = tmp.components(separatedBy: ",")[1]
        
        
        //changing from string to date
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "dd-MM-yy HH:mm"
        dateFormatter.locale = NSLocale(localeIdentifier: "en_US_POSIX") as Locale
        let exam_date = dateFormatter.date(from: tmp)
        
        
        //comparing between exam date and today
        if (exam_date! < today as Date){
            //if it is passed, font color is changed to grey
            cell.textLabel!.textColor = UIColor.lightGray
        }
        
        return cell
    }
    
    
    //checkbox setting
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        var str:String = ""
        
        if exam_list_view.cellForRow(at: indexPath)?.accessoryType == UITableViewCell.AccessoryType.checkmark{
            exam_list_view.cellForRow(at: indexPath)?.accessoryType = UITableViewCell.AccessoryType.none
            
        }else{
            exam_list_view.cellForRow(at: indexPath)?.accessoryType = UITableViewCell.AccessoryType.checkmark
        }
        
        str = (exam_list_view.cellForRow(at: indexPath)?.textLabel!.text)!
        print(str)
        str = str.components(separatedBy: ",")[0]
        print(str)
        appDelegate.changeStatusColumn(unit_name: str)
        
    }
    
    
    @IBAction func deleteBtn(_ sender: Any) {
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        
        //delete data if its status is "false"
        appDelegate.deleteExamInfo()
        
        notice_label.text = "the data is deleted!"
    }

    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        //present time
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "dd/MM/yy HH:mm"
        let dateString = dateFormatter.string(from: today as Date)
        notice_label.text = "present time : " + dateString
        
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        exam_list_array = appDelegate.getExamArray(stu_id: stu_id)
    }
    
    
    //passing the stu_id to other pages
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        
        if (segue.identifier == "gotoEdit"){
            let DestViewController:EditStudent = segue.destination as! EditStudent
            DestViewController.stu_id = stu_id
        }
        if (segue.identifier == "gotoAddExam"){
            let DestViewController:AddExam = segue.destination as! AddExam
            DestViewController.stu_id = stu_id
        }
    }
    

    

}
