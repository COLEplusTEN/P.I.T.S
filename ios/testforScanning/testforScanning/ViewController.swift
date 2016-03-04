//
//  ViewController.swift
//  testforScanning
//
//  Created by Jerry Liu on 2/5/16.
//  Copyright © 2016 Jerry Liu. All rights reserved.
//

import UIKit


class ViewController: UIViewController {
    
    let store = KCSAppdataStore.storeWithOptions([
        KCSStoreKeyCollectionName : "Events",
        KCSStoreKeyCollectionTemplateClass : Food.self
        ])
    
    @IBAction func submit(sender: AnyObject) {

        let food = Food()
        food.count = "16.00"
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
                    NSLog("Successfully saved event(s) (id='%@').", (objectsOrNil[0] as! NSObject).kinveyObjectId())
                }
            },
            withProgressBlock: nil
        )
        print("testforsdfsdf")
    }
    @IBAction func load(sender: AnyObject) {
        store.queryWithQuery(
            KCSQuery(onField: "unit", withExactMatchForValue: "test"),
            withCompletionBlock: { (objectsOrNil: [AnyObject]!, errorOrNil: NSError!) -> Void in
                if errorOrNil != nil {
                    //save failed
                    NSLog("Fetched failed, with error: %@", errorOrNil.localizedFailureReason!)
                } else {
                    //save was successful
                    NSLog("Successfully fetched event(s) (id='%@').", (objectsOrNil[0] as! NSObject).kinveyObjectId())
                    for object in objectsOrNil {
                        print(object)
                    }
                }
            },
            withProgressBlock: nil
        )
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

