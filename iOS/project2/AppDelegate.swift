//
//  AppDelegate.swift
//  project2
//
//  Created by jina son on 22/10/2018.
//  Copyright © 2018 jina son. All rights reserved.
//

import UIKit
import CoreData
import CoreLocation

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    
    var locationManager: CLLocationManager?
    var window: UIWindow?
    
    func getContext() -> NSManagedObjectContext {
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        return appDelegate.persistentContainer.viewContext
    }
    
    //store students infomation
    func storeStuInfo (student_id: Int, first_name: String, last_name: String, gender: String, course_study: String, age: Int, address: String){
        let context = getContext()
        //retrieve the entity that we just created
        let entity = NSEntityDescription.entity(forEntityName: "Student", in: context)
        let transc = NSManagedObject(entity: entity!, insertInto: context)
        
        //set the entity values
        transc.setValue(student_id, forKey: "student_id")
        transc.setValue(first_name, forKey: "first_name")
        transc.setValue(last_name, forKey: "last_name")
        transc.setValue(gender, forKey: "gender")
        transc.setValue(course_study, forKey: "course_study")
        transc.setValue(age, forKey: "age")
        transc.setValue(address, forKey: "address")
        
        //save the object
        do {
            try context.save()
        } catch let error as NSError {
            print("Could not save \(error), \(error.userInfo)")
        } catch {}
    }
    
    //updating students infomation
    func updateStuInfo (student_id: Int, first_name: String, last_name: String, gender: String, course_study: String, age: Int, address: String){
        
        let fetchRequest: NSFetchRequest<Student> = Student.fetchRequest()
        
        do{
            let searchResults = try getContext().fetch(fetchRequest)
            
            for trans in searchResults as [NSManagedObject] {
                if ((trans.value(forKey: "student_id") as! Int) == student_id){
                    trans.setValue(first_name, forKey: "first_name")
                    trans.setValue(last_name, forKey: "last_name")
                    trans.setValue(gender, forKey: "gender")
                    trans.setValue(course_study, forKey: "course_study")
                    trans.setValue(age, forKey: "age")
                    trans.setValue(address, forKey: "address")
                }
            }
        }catch {
            print ("Error with request: \(error)")
        }
    }
    
    //deleting students infomation
    func deleteStuInfo(stu_id:Int){
        let context = getContext()
        let fetchRequest: NSFetchRequest<Student> = Student.fetchRequest()
        
        do{
            let searchResults = try getContext().fetch(fetchRequest)
            
            for trans in searchResults as [NSManagedObject] {
                if ((trans.value(forKey: "student_id") as! Int) == stu_id){
                    context.delete(trans)
                }
            }
        }catch {
            print ("Error with request: \(error)")
        }
    }
    
    //get students infomation by one string
    func getStuInfo() -> String {
        var info = ""
        let fetchRequest: NSFetchRequest<Student> = Student.fetchRequest()
        
        do{
            let searchResults = try getContext().fetch(fetchRequest)
            
            print ("num of results = \(searchResults.count)")
            
            for trans in searchResults as [NSManagedObject] {
                let id = String (trans.value(forKey: "student_id") as! Int)
                let first_name = trans.value(forKey: "first_name") as! String
                let last_name = trans.value(forKey: "last_name") as! String
                let gender = trans.value(forKey: "gender") as! String
                let course_study = trans.value(forKey: "course_study") as! String
                let age = String (trans.value(forKey: "age") as! Int)
                let address = trans.value(forKey: "address") as! String
                
                info = info + id + ", " + first_name + ", " + last_name + ", " + gender + ", " + course_study + ", " + age + ", " + address + "\n"
            }
        }catch {
            print ("Error with request: \(error)")
        }
        return info;
    }
    
    //get specific student's column which user wants
    func getStuColumn(stu_id: Int, col: String) -> String {
        let fetchRequest: NSFetchRequest<Student> = Student.fetchRequest()
        
        do{
            let searchResults = try getContext().fetch(fetchRequest)
            
            for trans in searchResults as [NSManagedObject] {
                if ( (trans.value(forKey: "student_id")) as! Int == stu_id) {
                    if (col == "age"){
                        return String(trans.value(forKey: col) as! Int)
                    }else{
                        return trans.value(forKey: col) as! String
                    }
                }
            }
        }catch {
            print ("Error with request: \(error)")
        }
        return "error - no item";
    }
    
    
    //get student_id array
    func getStuIdArray() -> [Int] {
        var stu : [Int] = []
        let fetchRequest: NSFetchRequest<Student> = Student.fetchRequest()
        
        do{
            let searchResults = try getContext().fetch(fetchRequest)
            
            for trans in searchResults as [NSManagedObject] {
                stu.append((trans.value(forKey: "student_id")) as! Int)
            }
        }catch {
            print ("Error with request: \(error)")
        }
        return stu
    }
    
    //remove all student infomation
    func removeRecords () {
        let context = getContext()
        
        let deleteFetch = NSFetchRequest<NSFetchRequestResult>(entityName: "Student")
        let deleteRequest = NSBatchDeleteRequest(fetchRequest: deleteFetch)
        
        do {
            try context.execute(deleteRequest)
            try context.save()
        } catch {
            print("There was an error")
        }
    }
    
    
    
    
    //////////////////////////////exam/////////////////////////////////
    
    
    func storeExamInfo (unit_name: String, date_time: Date, location: String, stu_id: Int){
        let context = getContext()
        //retrieve the entity that we just created
        let entity = NSEntityDescription.entity(forEntityName: "Exam", in: context)
        let transc = NSManagedObject(entity: entity!, insertInto: context)
        
        //set the entity values
        transc.setValue(unit_name, forKey: "unit_name")
        transc.setValue(date_time, forKey: "date_time")
        transc.setValue(location, forKey: "location")
        transc.setValue(stu_id, forKey: "stu_id")
        transc.setValue("true", forKey: "status")
        
        //save the object
        do {
            try context.save()
        } catch let error as NSError {
            print("Could not save \(error), \(error.userInfo)")
        } catch {}
    }
    
    func getExamArray(stu_id: Int) -> [String] {
        var exam : [String] = []
        let fetchRequest: NSFetchRequest<Exam> = Exam.fetchRequest()
        var rowStr : String!
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "dd-MM-yy HH:mm"
        
        do{
            let searchResults = try getContext().fetch(fetchRequest)
            
            for trans in searchResults as [NSManagedObject] {
                if (trans.value(forKey: "stu_id") as! Int == stu_id) {
                    rowStr = ((trans.value(forKey: "unit_name")) as! String) + ", " + dateFormatter.string(from: trans.value(forKey: "date_time") as! Date) + ", " + ((trans.value(forKey: "location")) as! String)
                    
                    exam.append(rowStr)
                }
            }
        }catch {
            print ("Error with request: \(error)")
        }
        return exam
    }
    
    func getUnitNameArray(stu_id: Int) -> [String] {
        var exam : [String] = []
        let fetchRequest: NSFetchRequest<Exam> = Exam.fetchRequest()
        
        do{
            let searchResults = try getContext().fetch(fetchRequest)
            
            for trans in searchResults as [NSManagedObject] {
                if (trans.value(forKey: "stu_id") as! Int == stu_id) {
                    exam.append(trans.value(forKey: "unit_name") as! String)
                }
            }
        }catch {
            print ("Error with request: \(error)")
        }
        return exam
    }
    
    func changeStatusColumn(unit_name: String){
        let fetchRequest: NSFetchRequest<Exam> = Exam.fetchRequest()
        
        do{
            let searchResults = try getContext().fetch(fetchRequest)
            
            for trans in searchResults as [NSManagedObject] {
                if ( (trans.value(forKey: "unit_name")) as! String == unit_name) {
                    
                    if (trans.value(forKey: "status") as! String == "true"){
                        trans.setValue("false", forKey: "status")
                    }else{
                        trans.setValue("true", forKey: "status")
                    }
                }
            }
        }catch {
            print ("Error with request: \(error)")
        }
    }
    
    
    
    
    func deleteExamInfo(){
        let context = getContext()
        let fetchRequest: NSFetchRequest<Exam> = Exam.fetchRequest()
        
        do{
            let searchResults = try getContext().fetch(fetchRequest)
            
            for trans in searchResults as [NSManagedObject] {
                if ((trans.value(forKey: "status") as! String) == "false"){
                    context.delete(trans)
                }
            }
        }catch {
            print ("Error with request: \(error)")
        }
    }
    
    
    
    
    
    
    
    


    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application launch.
        locationManager = CLLocationManager()
        locationManager?.requestWhenInUseAuthorization()
        return true
    }

    func applicationWillResignActive(_ application: UIApplication) {
        // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
        // Use this method to pause ongoing tasks, disable timers, and invalidate graphics rendering callbacks. Games should use this method to pause the game.
    }

    func applicationDidEnterBackground(_ application: UIApplication) {
        // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
        // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
    }

    func applicationWillEnterForeground(_ application: UIApplication) {
        // Called as part of the transition from the background to the active state; here you can undo many of the changes made on entering the background.
    }

    func applicationDidBecomeActive(_ application: UIApplication) {
        // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
    }

    func applicationWillTerminate(_ application: UIApplication) {
        // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
        // Saves changes in the application's managed object context before the application terminates.
        self.saveContext()
    }

    // MARK: - Core Data stack

    lazy var persistentContainer: NSPersistentContainer = {
        /*
         The persistent container for the application. This implementation
         creates and returns a container, having loaded the store for the
         application to it. This property is optional since there are legitimate
         error conditions that could cause the creation of the store to fail.
        */
        let container = NSPersistentContainer(name: "project2")
        container.loadPersistentStores(completionHandler: { (storeDescription, error) in
            if let error = error as NSError? {
                // Replace this implementation with code to handle the error appropriately.
                // fatalError() causes the application to generate a crash log and terminate. You should not use this function in a shipping application, although it may be useful during development.
                 
                /*
                 Typical reasons for an error here include:
                 * The parent directory does not exist, cannot be created, or disallows writing.
                 * The persistent store is not accessible, due to permissions or data protection when the device is locked.
                 * The device is out of space.
                 * The store could not be migrated to the current model version.
                 Check the error message to determine what the actual problem was.
                 */
                fatalError("Unresolved error \(error), \(error.userInfo)")
            }
        })
        return container
    }()

    // MARK: - Core Data Saving support

    func saveContext () {
        let context = persistentContainer.viewContext
        if context.hasChanges {
            do {
                try context.save()
            } catch {
                // Replace this implementation with code to handle the error appropriately.
                // fatalError() causes the application to generate a crash log and terminate. You should not use this function in a shipping application, although it may be useful during development.
                let nserror = error as NSError
                fatalError("Unresolved error \(nserror), \(nserror.userInfo)")
            }
        }
    }

}

