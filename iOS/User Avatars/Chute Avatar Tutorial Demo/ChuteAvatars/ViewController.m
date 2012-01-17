//
//  ViewController.m
//  ChuteAvatars
//
//  Created by Brandon Coston on 12/30/11.
//  Copyright (c) 2011 __MyCompanyName__. All rights reserved.
//

#import "ViewController.h"
#import "avatarView.h"

@implementation ViewController
@synthesize chute;
@synthesize parcel;

-(void)dealloc{
    [chute release];
    [parcel release];
    [super dealloc];
}

-(BOOL)textFieldShouldReturn:(UITextField *)textField{
    [textField resignFirstResponder];
    return YES;
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Release any cached data, images, etc that aren't in use.
}

-(void)setMetadata{
    [GCAsset searchMetaDataForKey:@"CAT_USER_ID" andValue:[userID text] inBackgroundWithCompletion:^(GCResponse *response){
        if([response isSuccessful]){
            NSArray *assetArray = [response object];
            if([assetArray count] > 0){
                for(GCAsset *old in assetArray){
                    [old deleteMetaDataForKey:@"CAT_USER_ID"];
                }
            }
        }
        response = [[self parcel] serverAssets];
        if([response isSuccessful]){
            NSArray *array = [response object];
            if(array.count > 0){
                GCAsset *_asset = [array objectAtIndex:0];
                [_asset setMetaData:[userID text] forKey:@"CAT_USER_ID" inBackgroundWithCompletion:^(BOOL successful){
                    [self hideHUD];
                    [[self navigationController] setNavigationBarHidden:NO];
                    [[self navigationController] popViewControllerAnimated:YES];
                }];
            }
        }
    }];
}

-(void)imagePickerController:(UIImagePickerController *)picker didFinishPickingMediaWithInfo:(NSDictionary *)info{
    [self dismissModalViewControllerAnimated:YES];
    
    UIImage *originalImage, *editedImage, *imageToSave;
    
    editedImage = (UIImage *) [info objectForKey:UIImagePickerControllerEditedImage];
    originalImage = (UIImage *) [info objectForKey:UIImagePickerControllerOriginalImage];
    
    if (editedImage) {
        imageToSave = editedImage;
    } else {
        imageToSave = originalImage;
    }
    if(![[GCAccount sharedManager] assetsLibrary]){
        ALAssetsLibrary *temp = [[ALAssetsLibrary alloc] init];
        [[GCAccount sharedManager] setAssetsLibrary:temp];
        [temp release];
    }
    ALAssetsLibrary *library = [[GCAccount sharedManager] assetsLibrary];
    [self showHUDWithTitle:@"uploading avatar" andOpacity:.75];
    [library writeImageToSavedPhotosAlbum:[imageToSave CGImage] metadata:[info objectForKey:UIImagePickerControllerMediaMetadata] completionBlock:^(NSURL *assetURL, NSError *error){
        if(assetURL){
            [library assetForURL:assetURL resultBlock:^(ALAsset* _alasset){
                
                GCAsset *_asset = [[GCAsset alloc] init];
                [_asset setAlAsset:_alasset];
                GCParcel *_parcel = [GCParcel objectWithAssets:[NSArray arrayWithObject:_asset] andChutes:[NSArray arrayWithObject:[self chute]]];
                [self setParcel:_parcel];
                [[self parcel] startUploadWithTarget:self andSelector:@selector(setMetadata)];
                [_asset release];
            } failureBlock:^(NSError* error){
            }];
        }
    }];
}
-(void)imagePickerControllerDidCancel:(UIImagePickerController *)picker{
    [self dismissModalViewControllerAnimated:YES];
}

#pragma mark - View lifecycle

-(IBAction)chooseAvatarClicked:(id)sender{
    if(![self chute])
        return;
    if([[[userID text] stringByReplacingOccurrencesOfString:@" " withString:@""] length] == 0)
        return;
    UIImagePickerController *picker = [[UIImagePickerController alloc] init];
    [picker setDelegate:self];
    [picker setAllowsEditing:YES];
    [picker setSourceType:UIImagePickerControllerSourceTypePhotoLibrary];
    [self presentModalViewController:picker animated:YES];
    [picker release];
}
-(IBAction)viewAvatarClicked:(id)sender{
    if([[[userID text] stringByReplacingOccurrencesOfString:@" " withString:@""] length] == 0)
        return;
    avatarView *av = [[avatarView alloc] init];
    [av setUserID:[userID text]];
    [[self navigationController] pushViewController:av animated:YES];
    [av release];
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    GCResponse *response = [GCChute findById:@"1435"];
    if([response isSuccessful]){
        [self setChute:[response object]];
    }
    else{
        NSLog(@"error retreiving chute");
    }
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
}

- (void)viewWillDisappear:(BOOL)animated
{
	[super viewWillDisappear:animated];
}

- (void)viewDidDisappear:(BOOL)animated
{
	[super viewDidDisappear:animated];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
}

@end
