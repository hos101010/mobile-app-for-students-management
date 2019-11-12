//
//  WebController.swift
//  project2
//
//  Created by jina son on 28/10/2018.
//  Copyright © 2018 jina son. All rights reserved.
//

import UIKit

class WebController: UIViewController {

    @IBOutlet weak var webView: UIWebView!
    override func viewDidLoad() {
        super.viewDidLoad()
        
        if let myURL = URL (string:"https://www.westernsydney.edu.au"){
            let myURLRequest = URLRequest(url:myURL)
            webView.loadRequest(myURLRequest)
        }

        // Do any additional setup after loading the view.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
