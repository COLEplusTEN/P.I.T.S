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
        food.unit = "lid"
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
                    NSLog("Successfully saved event (id='%@').", (objectsOrNil[0] as! NSObject).kinveyObjectId())
                }
            },
            withProgressBlock: nil
        )
        print("testforsdfsdf")
    }
    @IBAction func load(sender: AnyObject) {
        store.loadObjectWithID(
            [ "56d0c9560c0dec967114e470", "56cbe344a9d67c8f640cc756" ],
            withCompletionBlock: { (objectsOrNil: [AnyObject]!, errorOrNil: NSError!) -> Void in
                if errorOrNil == nil {
                    NSLog("successfully loaded objects: %@", objectsOrNil)
                } else {
                    NSLog("error occurred: %@", errorOrNil)
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

