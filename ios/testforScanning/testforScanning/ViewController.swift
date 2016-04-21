//
//  ViewController.swift
//  testforScanning
//
//  Created by Jerry Liu on 2/5/16.
//  Copyright © 2016 Jerry Liu. All rights reserved.
//

import UIKit

class ViewController: UIViewController, UITextFieldDelegate {
    
    @IBOutlet weak var background: UIImageView!
    @IBOutlet weak var NameLB: UILabel!
    @IBOutlet weak var UnitLB: UILabel!
    @IBOutlet var Name: UITextField!
    @IBOutlet weak var CurrentAmountLB: UILabel!
    @IBOutlet var CurrentAmount: UITextField!
    @IBOutlet var Submit: UIButton!
    @IBOutlet var one: UIButton!
    @IBOutlet var two: UIButton!
    @IBOutlet var three: UIButton!
    @IBOutlet var four: UIButton!
    @IBOutlet var five: UIButton!
    @IBOutlet var six: UIButton!
    @IBOutlet var seven: UIButton!
    @IBOutlet var eight: UIButton!
    @IBOutlet var nine: UIButton!
    @IBOutlet var zero: UIButton!
    @IBOutlet var period: UIButton!
    @IBOutlet var back: UIButton!
    var roma:String?
    var unit:String?
    var usFoods:String?
    var walmartHyvee: String?
    var numberOfItem:UInt?
    
   
    
    let store = KCSAppdataStore.storeWithOptions([
        KCSStoreKeyCollectionName : "eventsCollection",
        KCSStoreKeyCollectionTemplateClass : Food.self
        ])
    
    
    @IBAction func checknums(sender: AnyObject) {
        var number = sender.currentTitle
        CurrentAmount.text = CurrentAmount.text!  + number!!
        
        
    }
    
    
    @IBAction func backspace(sender: AnyObject) {
        
        if (CurrentAmount.text!.characters.count != 0) {
            CurrentAmount.text!.removeAtIndex(CurrentAmount.text!.endIndex.predecessor())
        
        }
        
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = UIColor(patternImage: UIImage(named: "tropical-forest3.png")!)
        self.Name.becomeFirstResponder()
        self.one.backgroundColor = UIColor.whiteColor()
        self.two.backgroundColor = UIColor.whiteColor()
        self.three.backgroundColor = UIColor.whiteColor()
        self.four.backgroundColor = UIColor.whiteColor()
        self.five.backgroundColor = UIColor.whiteColor()
        self.six.backgroundColor = UIColor.whiteColor()
        self.seven.backgroundColor = UIColor.whiteColor()
        self.eight.backgroundColor = UIColor.whiteColor()
        self.nine.backgroundColor = UIColor.whiteColor()
        self.zero.backgroundColor = UIColor.whiteColor()
        self.period.backgroundColor = UIColor.whiteColor()
        self.back.backgroundColor = UIColor.whiteColor()
        self.CurrentAmount.delegate = self
        
    }
    
    //Submit Button
    @IBAction func submit(sender: AnyObject) {
            checkItemExistsInDatabase(Name.text!)
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
                    self.roma = c?.roma
                    self.usFoods = c?.usFoods
                    self.walmartHyvee = c?.walmartHyvee
                }
            }
            }, withProgressBlock: nil)
    }
    
    func checkItemExistsInDatabase(var itemName:String){
        let query = KCSQuery(onField: "_id", withExactMatchForValue: itemName)
        store.countWithQuery(query, completion: { (count: UInt, errorOrNil: NSError!) -> Void in
            do{
                self.numberOfItem = count
            if(self.numberOfItem != 0 && self.numberOfItem != nil){
                let food = Food()
                food._id = self.Name.text
                food.count = self.CurrentAmount.text
                food.roma = self.roma
                food.unit = self.UnitLB.text
                food.usFoods = self.usFoods
                food.walmartHyvee = self.walmartHyvee
                self.store.saveObject(
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
                //Display success popup
                self.displayPopupWithTitle("Submitted Succesfully", message: "There are now \(food.count!) \(food.unit!) of \(food._id!) in the database.", alert:false)
                var dispatchTime: dispatch_time_t = dispatch_time(DISPATCH_TIME_NOW, Int64(1.5 * Double(NSEC_PER_SEC)))
                dispatch_after(dispatchTime, dispatch_get_main_queue(), {
                    self.dismissViewControllerAnimated(false, completion: nil)
                    self.Name.becomeFirstResponder()
                })
                //Put focus back on name textfield and clear the text
                self.CurrentAmount.text = "0"
                self.Name.text = ""
                self.UnitLB.text = ""
            }
            else{
                self.displayPopupWithTitle("Error", message: "The item does not exist in the database. Please try again or create the item in the database.", alert: true)
            }
            }
            catch{
                self.displayPopupWithTitle("Error Uploading", message: "Try entering an item and amount first.", alert:true)
        }
            //             var fetchComplete = CACurrentMediaTime();
            //            print("fetch in progress \(fetchComplete)")
        })
    }
    
    //Display Popup
    func displayPopupWithTitle(title:String, message:String, alert:Bool) {
        if(alert == false){
            let uiAlertController:UIAlertController = UIAlertController(title: title, message: message, preferredStyle: .Alert)
            self.presentViewController(uiAlertController, animated: true, completion: nil)}
            //if alert == true, display alert instead of popup
        else{
            let uiAlertController:UIAlertController = UIAlertController(title: title,
                message: message, preferredStyle: UIAlertControllerStyle.Alert); uiAlertController.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.Cancel,
                    handler:{(action:UIAlertAction)->Void in  }))
            self.presentViewController(uiAlertController, animated: true, completion: nil)
        }
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func textField(textField: UITextField, shouldChangeCharactersInRange range: NSRange, replacementString string: String) -> Bool { // return false to not change text
        
        if (textField == CurrentAmount){
        switch string {
        case "0","1","2","3","4","5","6","7","8","9":
            return true
        case ".":
            let array = textField.text?.characters.map { String($0) }
            var decimalCount = 0
            for character in array! {
                if character == "." {
                    decimalCount++
                }
            }
            if decimalCount == 1 {
                return false
            } else {
                return true
            }
        default:
            let array = string.characters.map { String($0) }
            if array.count == 0 {
                return true
            }
            return false
        }
        }
        else {
            return true

        }
    }
    
}