//
//  ViewController.swift
//  testforScanning
//
//  Created by Jerry Liu on 2/5/16.
//  Copyright © 2016 Jerry Liu. All rights reserved.
//

import UIKit


class ViewController: UIViewController {
    
    @IBAction func submit(sender: AnyObject) {
       
        
        
        let store = KCSAppdataStore.storeWithOptions([
            KCSStoreKeyCollectionName : "Events",
            KCSStoreKeyCollectionTemplateClass : Food.self
            ])
        
        
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
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    


}

