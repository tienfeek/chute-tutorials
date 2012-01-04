//
//  ViewController.h
//  ChuteImageUploader
//
//  Created by Brandon Coston on 12/13/11.
//  Copyright (c) 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "GetChute.h"
#import "UploadPicker.h"
#import "GCCloudGallery.h"

@interface ViewController : UIViewController{
    IBOutlet UIView *PBForeground;
    IBOutlet UIView *PBBackround;
}
@property (nonatomic, retain) GCChute *chute;

-(IBAction)showUploader;
-(IBAction)showGallery;

@end
