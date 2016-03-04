//
//  ViewController.swift
//  testforScanning
//
//  Created by Jerry Liu on 2/5/16.
//  Copyright © 2016 Jerry Liu. All rights reserved.
//

import UIKit
import SwiftyJSON

class ViewController: UIViewController {
    
    @IBOutlet weak var id: UILabel!
    
    @IBOutlet weak var countField: UITextField!
    
    let store = KCSAppdataStore.storeWithOptions([
        KCSStoreKeyCollectionName : "Events",
        KCSStoreKeyCollectionTemplateClass : Food.self
        ])
    
    @IBAction func submit(sender: AnyObject) {

        let food = Food()
        food._id = "lettuce"
        food.count = countField.text
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
//        store.queryWithQuery(
//            KCSQuery(onField: "unit", withExactMatchForValue: "can"),
//            withCompletionBlock: { (objectsOrNil: [AnyObject]!, errorOrNil: NSError!) -> Void in
//                if errorOrNil != nil {
//                    //save failed
//                    NSLog("Fetched failed, with error: %@", errorOrNil.localizedFailureReason!)
//                } else {
//                    //save was successful
//                    NSLog("Successfully fetched event(s).")
//                    for object in objectsOrNil {
//                        print(object)
//                        let json = JSON(object)
//                        if let string = json.rawString() {
//                            print(string)
//                        }
//                    }
//                }
//            },
//            withProgressBlock: nil
//        )
        //func fetchLot() {
        let query:KCSQuery = KCSQuery(onField: "_id", withExactMatchForValue: "lettuce")
        store.queryWithQuery(query, withCompletionBlock: { (objectsOrNil: [AnyObject]!, errorOrNil: NSError!) -> Void in
            for obj in objectsOrNil {
                print(obj)
            }
            
            if let results = objectsOrNil as? NSArray
            {
                for r in results {
                    let c = r as? Food
                    self.id?.text = c?._id
                    self.countField?.text = c?.count
                }
            }
            }, withProgressBlock: nil)
    }

       override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    


}

