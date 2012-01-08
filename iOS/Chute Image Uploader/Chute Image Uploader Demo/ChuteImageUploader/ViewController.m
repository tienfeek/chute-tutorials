//
//  ViewController.m
//  ChuteImageUploader
//
//  Created by Brandon Coston on 12/13/11.
//  Copyright (c) 2011 __MyCompanyName__. All rights reserved.
//

#import "ViewController.h"

@implementation ViewController
@synthesize chute;


- (void) showProgressIndicator {
    [PBBackround setHidden:NO];
    [PBForeground setHidden:NO];
}

- (void) hideProgressIndicator {
    [PBBackround setHidden:YES];
    [PBForeground setHidden:YES];
}

- (void) progressIndicator:(NSNotification *) notification {
    if ([[GCUploader sharedUploader] progress] > 0 && [[GCUploader sharedUploader] progress] < 1) {
        [self showProgressIndicator];
        
        [UIView animateWithDuration:0.1 delay:0.0 options:UIViewAnimationOptionAllowUserInteraction animations:^{
            [PBForeground setFrame:CGRectMake(PBForeground.frame.origin.x, PBForeground.frame.origin.y, 300*[[GCUploader sharedUploader] progress], 20)];
        } completion:^(BOOL finished) {}];
        return;
    }
    [self hideProgressIndicator];
}


-(IBAction)showUploader{
    UploadPicker *picker = [[UploadPicker alloc] init];
    [picker setChute:[self chute]];
    [[self navigationController] pushViewController:picker animated:YES];
    [picker release];
}

-(IBAction)showGallery{
    GCResponse *response = [[self chute] assets];
    if([response isSuccessful]){
        GCCloudGallery *gallery = [[GCCloudGallery alloc] init];
        [gallery setObjects:[response object]];
        [[self navigationController] pushViewController:gallery animated:YES];
        [gallery release];
    }
}

-(void)dealloc{
    [chute release];
    [super dealloc];
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    [self hideProgressIndicator];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(progressIndicator:) name:GCUploaderProgressChanged object:nil];
}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
    [GCLoginViewController presentInController:self];
    if([[GCAccount sharedManager] accessToken]){
        if([[NSUserDefaults standardUserDefaults] objectForKey:@"chuteID"]){
            [GCChute findById:[[NSUserDefaults standardUserDefaults] objectForKey:@"chuteID"] inBackgroundWithCompletion:^(GCResponse *response){
                if([response isSuccessful]){
                    [self setChute:[response object]];
                }
            }];
        }
        else{
            GCChute *_newChute = [GCChute new];
            
            [_newChute setName:@"Uploads"];
            [_newChute setPermissionView:GCPermissionTypeMembers];
            [_newChute setPermissionAddMembers:GCPermissionTypeMembers];
            [_newChute setPermissionAddPhotos:GCPermissionTypeMembers];
            [_newChute setPermissionAddComments:GCPermissionTypeMembers];
            [_newChute setModeratePhotos:GCPermissionTypePublic];
            [_newChute setModerateMembers:GCPermissionTypePublic];
            [_newChute setModerateComments:GCPermissionTypePublic];
            
            [_newChute saveInBackgroundWithCompletion:^(BOOL success, NSError *error){
                if(success){
                    [[NSUserDefaults standardUserDefaults] setObject:[_newChute objectID] forKey:@"chuteID"];
                    [self setChute:_newChute];
                }
            }];
        }
    }
}
@end
