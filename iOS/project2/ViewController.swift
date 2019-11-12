//
//  ViewController.swift
//  project2
//
//  Created by jina son on 22/10/2018.
//  Copyright © 2018 jina son. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    //delete all records
    @IBAction func ClearRecords(_ sender: Any) {
        
        let alertContoller = UIAlertController(title: "Caution", message: "You really want to clear all student info?", preferredStyle: .alert)
        
        let OKAction = UIAlertAction(title: "OK", style: .default) {
            (action:UIAlertAction!) in
                let appDelegate = UIApplication.shared.delegate as! AppDelegate
                appDelegate.removeRecords();
        }
        
        alertContoller.addAction(OKAction)
        
        let cancelAction = UIAlertAction(title: "Cancel", style: .cancel){
            (action:UIAlertAction!) in
            print("Cancel button tapped");
        }
        alertContoller.addAction(cancelAction)
        
        self.present(alertContoller,animated: true, completion: nil)
            
    }
    
}

