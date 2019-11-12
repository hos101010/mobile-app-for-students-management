//
//  AddExam.swift
//  project2
//
//  Created by jina son on 28/10/2018.
//  Copyright © 2018 jina son. All rights reserved.
//

import UIKit

class AddExam: UIViewController {

    @IBOutlet weak var date_picker: UIDatePicker!
    @IBOutlet weak var location_txt: UITextField!
    @IBOutlet weak var name_txt: UITextField!
    @IBOutlet weak var notice_label: UILabel!
    
    var stu_id : Int = 0
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        notice_label.text = String(stu_id) + "'s exam"
        // Do any additional setup after loading the view.
    }
    
    
    //save the data
    @IBAction func submitBtn(_ sender: Any) {
        
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        appDelegate.storeExamInfo(unit_name: name_txt.text!, date_time: date_picker.date, location: location_txt.text!, stu_id: stu_id)
        
        notice_label.text = "saved the data!"
        
    }
    
    
    //pass the stu_id to other pages
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        
        if (segue.identifier == "gotoEdit"){
            let DestViewController:EditStudent = segue.destination as! EditStudent
             DestViewController.stu_id = stu_id
        }
        if (segue.identifier == "gotoViewExam"){
            let DestViewController:ViewExam = segue.destination as! ViewExam
            DestViewController.stu_id = stu_id
        }
    }
    
}
