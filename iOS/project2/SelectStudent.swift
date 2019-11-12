//
//  SelectStudent.swift
//  project2
//
//  Created by jina son on 23/10/2018.
//  Copyright © 2018 jina son. All rights reserved.
//

import UIKit

class SelectStudent: UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    
    @IBOutlet weak var stu_list_view: UITableView!
    
    var stu_list:[Int] = []
    var selected:Int = 39
    
    
    //setting table view
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return stu_list.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell:UITableViewCell = stu_list_view.dequeueReusableCell(withIdentifier: "myCell", for: indexPath)
        
        cell.textLabel!.text = String (stu_list[indexPath.row])
        return cell;
    }
    
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        stu_list_view.deselectRow(at: indexPath, animated: true)
    }
   
    
    //passing the stu_id to other pages
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        
        if (segue.identifier == "gotoEdit"){
            let DestViewController:EditStudent = segue.destination as! EditStudent
            let index = stu_list_view.indexPathForSelectedRow?.row
            DestViewController.stu_id = stu_list[index!]
        }
    }
    

    override func viewDidLoad() {
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        stu_list = appDelegate.getStuIdArray()
        
        super.viewDidLoad()
        
    }

}
