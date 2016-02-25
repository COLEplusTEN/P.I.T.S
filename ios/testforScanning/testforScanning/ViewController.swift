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
        food.count = "12.01"
        food.roma = "12.01"
        food.unit = "can"
        food.usFoods = "12.01"
        food.walmartHyvee = "12.01"
        
        store.saveObject(
            food,
            withCompletionBlock: { (objectsOrNil: [AnyObject]!, errorOrNil: NSError!) -> Void in
                if errorOrNil != nil {
                    //save failed
                    NSLog("Save failed, with error: %@", errorOrNil.localizedFailureReason!)
                } else {
                    //save was successful
                    NSLog("Successfully saved event (id='%@').", (objectsOrNil[0] as! NSObject).kinveyObjectId())
                }
            },
            withProgressBlock: nil
        )
        print("testforsdfsdf")
    }
    store.loadObjectWithID (
    event.entityId,
    withCompletionBlock: { (objectsOrNil: [AnyObject]!, errorOrNil: NSError!) -> Void in
    if errorOrNil == nil {
    NSLog("successful reload: %@", objectsOrNil[0] as! NSObject) // event updated
    } else {
    NSLog("error occurred: %@", errorOrNil)
    }
    },
    withProgressBlock: nil
    )
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    


}

