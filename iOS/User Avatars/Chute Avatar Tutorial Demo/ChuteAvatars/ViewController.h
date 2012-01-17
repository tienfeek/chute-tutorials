//
//  ViewController.h
//  ChuteAvatars
//
//  Created by Brandon Coston on 12/30/11.
//  Copyright (c) 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "GetChute.h"

@interface ViewController : GCUIBaseViewController <UITextFieldDelegate, UIImagePickerControllerDelegate>{
    GCChute *chute;
    GCParcel *parcel;
    IBOutlet UITextField *userID;
}

@property (nonatomic, retain) GCChute *chute;
@property (nonatomic, retain) GCParcel *parcel;

-(IBAction)chooseAvatarClicked:(id)sender;
-(IBAction)viewAvatarClicked:(id)sender;

@end
