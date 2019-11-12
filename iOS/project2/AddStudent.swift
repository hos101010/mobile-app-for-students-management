//
//  AddStudent.swift
//  project2
//
//  Created by jina son on 22/10/2018.
//  Copyright © 2018 jina son. All rights reserved.
//

import UIKit

class AddStudent: UIViewController {
    
    
    @IBOutlet weak var notice_label: UILabel!
    @IBOutlet weak var address_txt: UITextField!
    @IBOutlet weak var age_label: UILabel!
    @IBOutlet weak var age_stepper: UIStepper!
    @IBOutlet weak var course_txt: UITextField!
    @IBOutlet weak var gender_seg: UISegmentedControl!
    @IBOutlet weak var last_name_txt: UITextField!
    @IBOutlet weak var first_name_txt: UITextField!
    @IBOutlet weak var id_txt: UITextField!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        age_stepper.wraps = true
        age_stepper.autorepeat = true
        age_stepper.maximumValue = 99
    }
    
    @IBAction func StepperChanged(_ sender: Any) {
        let step = Int(age_stepper.value)
        age_label.text = String(step)
    }
    
    
    //save student's information from input data by user
    @IBAction func SaveDataBtn(_ sender: Any) {
        
        let genderStr:String
        
        if self.gender_seg.selectedSegmentIndex == 0 {
            genderStr = "female"
        } else {
            genderStr = "male"
        }
        
        
       let appDelegate = UIApplication.shared.delegate as! AppDelegate
        
        appDelegate.storeStuInfo(student_id: Int (id_txt.text!)!, first_name: first_name_txt.text!, last_name: last_name_txt.text!, gender: genderStr, course_study: course_txt.text!, age: Int(age_label.text!)!, address: address_txt.text!)
        
        notice_label.text = "saved the data!"
    }
    

}
