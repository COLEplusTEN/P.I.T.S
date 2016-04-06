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
    @IBOutlet var Name: UITextField!
    @IBOutlet weak var CurrentAmountLB: UILabel!
    @IBOutlet var CurrentAmount: UITextField!
    
    let store = KCSAppdataStore.storeWithOptions([
        KCSStoreKeyCollectionName : "eventsCollection",
        KCSStoreKeyCollectionTemplateClass : Food.self
        ])
    
    @IBAction func submit(sender: AnyObject) {
        
        let food = Food()
        food._id = Name.text
        food.count = CurrentAmount.text
        food.roma = "16.00"
        food.unit = "test"
        food.usFoods = "16.00"
        food.walmartHyvee = "16.00"
        
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
    }
    @IBAction func load(sender: AnyObject) {
        let query:KCSQuery = KCSQuery(onField: "_id", withExactMatchForValue: "lettuce")
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
                }
            }
            }, withProgressBlock: nil)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = UIColor(patternImage: UIImage(named: "05-01.jpg")!)
        self.Name.becomeFirstResponder()
        let circlePath = UIBezierPath(arcCenter: CGPoint(x: 205, y: 485), radius: CGFloat(25), startAngle: CGFloat(0), endAngle:CGFloat(M_PI * 2), clockwise: true)
        
        let shapeLayer = CAShapeLayer()
        shapeLayer.path = circlePath.CGPath
        shapeLayer.fillColor = UIColor.whiteColor().CGColor
        view.layer.addSublayer(shapeLayer)
    }
    
    //If the scangun returns, query the database for that object
    func textFieldShouldReturn(textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        var itemName:String = Name.text!
        fetchItemFromDatabase(itemName)
        if (textField == self.Name) {
            self.CurrentAmount.becomeFirstResponder()
            
        }
        
        return true
        //self.CurrentAmount.becomeFirstResponder()
    }
    
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
                }
            }
            }, withProgressBlock: nil)
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    
    
    
}