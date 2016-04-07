//
//  ViewController.swift
//  testforScanning
//
//  Created by Jerry Liu on 2/5/16.
//  Copyright © 2016 Jerry Liu. All rights reserved.
//

import UIKit

class ViewController: UIViewController, UITextFieldDelegate {
    
    @IBOutlet weak var NameLB: UILabel!
    @IBOutlet weak var UnitLB: UILabel!
    @IBOutlet var Name: UITextField!
    @IBOutlet weak var CurrentAmountLB: UILabel!
    @IBOutlet var CurrentAmount: UITextField!
    
    let store = KCSAppdataStore.storeWithOptions([
        KCSStoreKeyCollectionName : "eventsCollection",
        KCSStoreKeyCollectionTemplateClass : Food.self
        ])
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = UIColor(patternImage: UIImage(named: "05-01.jpg")!)
        self.Name.becomeFirstResponder()
        
    }
    
//Submit Button
    @IBAction func submit(sender: AnyObject) {
        
        let food = Food()
        food._id = Name.text
        food.count = CurrentAmount.text
        
//        func 
//        let toastLabel = UILabel(frame: CGRectMake(self.view.frame.size.width/2 - 150, self.view.frame.size.height-100, 300, 35))
//        toastLabel.backgroundColor = UIColor.blackColor()
//        toastLabel.textColor = UIColor.whiteColor()
//        toastLabel.textAlignment = NSTextAlignment.Center;
//        self.view.addSubview(toastLabel)
//        toastLabel.text = "hello man..."
//        toastLabel.alpha = 1.0
//        toastLabel.layer.cornerRadius = 10;
//        toastLabel.clipsToBounds  =  true
        
        store.saveObject(
            food,
            withCompletionBlock: { (objectsOrNil: [AnyObject]!, errorOrNil: NSError!) -> Void in
                if errorOrNil != nil {
                    //save failed
                    NSLog("Save failed, with error: %@", errorOrNil.localizedFailureReason!)
                } else {
                    //save was successful
                    NSLog("Successfully saved event(s).")
                }
            },
            withProgressBlock: nil
        )
         displayAlertControllerWithTitle("👍 Good Job 👍", message: "Submitted Successfully!")
        //Put focus back on name textfield and clear the text
        self.Name.becomeFirstResponder()
        Name.text = ""
        CurrentAmount.text = "0"
    }
    
        //If the scangun inputs return/enter, automatically query the database for that object
    func textFieldShouldReturn(textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        var itemName:String = Name.text!
        fetchItemFromDatabase(itemName)
        if (textField == self.Name) {
            self.CurrentAmount.becomeFirstResponder()
            
        }
        
        return true
    }
    
    //Perform Database query
    func fetchItemFromDatabase(var itemName:String){
        let query:KCSQuery = KCSQuery(onField: "_id", withExactMatchForValue: itemName)
        store.queryWithQuery(query, withCompletionBlock: { (objectsOrNil: [AnyObject]!, errorOrNil: NSError!) -> Void in
            for obj in objectsOrNil {
                print(obj)
            }
            
            if let results = objectsOrNil as? NSArray
            {
                for r in results {
                    let c = r as? Food
                    self.Name?.text = c?._id
                    self.CurrentAmount?.text = c?.count
                    self.UnitLB?.text = c?.unit
                }
            }
            }, withProgressBlock: nil)
    }
    
    //Display Alert
    func displayAlertControllerWithTitle(title:String, message:String) {
        let uiAlertController:UIAlertController = UIAlertController(title: title,
            message: message, preferredStyle: UIAlertControllerStyle.Alert); uiAlertController.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.Cancel,
                handler:{(action:UIAlertAction)->Void in  }))
        self.presentViewController(uiAlertController, animated: true, completion: nil)
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
}