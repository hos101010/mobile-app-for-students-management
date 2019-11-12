//
//  EditStudent.swift
//  project2
//
//  Created by jina son on 22/10/2018.
//  Copyright © 2018 jina son. All rights reserved.
//

import UIKit

class EditStudent: UIViewController{
    
    @IBOutlet weak var notice_label: UILabel!
    @IBOutlet weak var stu_id_label: UILabel!
    @IBOutlet weak var first_name_txt: UITextField!
    @IBOutlet weak var last_name_txt: UITextField!
    @IBOutlet weak var gender_seg: UISegmentedControl!
    @IBOutlet weak var course_txt: UITextField!
    @IBOutlet weak var age_label: UILabel!
    @IBOutlet weak var age_stepper: UIStepper!
    @IBOutlet weak var address_txt: UITextField!
    
    var stu_id:Int = 0
    
    
    //show student's information from core data
    override func viewDidLoad() {
        super.viewDidLoad()
        age_stepper.wraps = true
        age_stepper.autorepeat = true
        age_stepper.maximumValue = 99
        
        stu_id_label.text = String(stu_id)
        
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        first_name_txt.text = appDelegate.getStuColumn(stu_id: stu_id, col: "first_name")
        last_name_txt.text = appDelegate.getStuColumn(stu_id: stu_id, col: "last_name")
        course_txt.text = appDelegate.getStuColumn(stu_id: stu_id, col: "course_study")
        age_label.text = appDelegate.getStuColumn(stu_id: stu_id, col: "age")
        address_txt.text = appDelegate.getStuColumn(stu_id: stu_id, col: "address")
        
        if (appDelegate.getStuColumn(stu_id: stu_id, col: "gender") == "female"){
            gender_seg.selectedSegmentIndex = 0
        } else {
            gender_seg.selectedSegmentIndex = 1
        }
    }
    
    @IBAction func StepperChanged(_ sender: Any) {
        let step = Int(age_stepper.value)
        age_label.text = String(step)
    }
    
    
    //update student information from the page
    @IBAction func EditBtn(_ sender: Any) {
        let genderStr:String
        
        if self.gender_seg.selectedSegmentIndex == 0 {
            genderStr = "female"
        } else {
            genderStr = "male"
        }
        
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        
        appDelegate.updateStuInfo(student_id: stu_id, first_name: first_name_txt.text!, last_name: last_name_txt.text!, gender: genderStr, course_study: course_txt.text!, age: Int(age_label.text!)!, address: address_txt.text!)
        
        notice_label.text = "changed the data!"
    }
    
    
    //delete the student information
    @IBAction func DeleteBtn(_ sender: Any) {
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        appDelegate.deleteStuInfo(stu_id: stu_id)
        notice_label.text = "deleted the data!"
    }
   
    //passing the stu_id to other pages
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        
        if (segue.identifier == "gotoNav"){
            let DestViewController:UINavigationController = segue.destination as! UINavigationController
            let targetController = DestViewController.topViewController as! MapController
            targetController.stu_address = address_txt.text!
        }
        
        if (segue.identifier == "gotoAddExam"){
            let DestViewController:AddExam = segue.destination as! AddExam
            DestViewController.stu_id = stu_id
        }
        if (segue.identifier == "gotoViewExam"){
            let DestViewController:ViewExam = segue.destination as! ViewExam
            DestViewController.stu_id = stu_id
        }
        
    }

}
