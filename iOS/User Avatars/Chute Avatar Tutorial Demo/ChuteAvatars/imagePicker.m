//
//  imagePicker.m
//  ChuteAvatars
//
//  Created by Brandon Coston on 1/1/12.
//  Copyright (c) 2011 __MyCompanyName__. All rights reserved.
//

#import "imagePicker.h"

@implementation imagePicker
@synthesize userID, chute, asset;

-(void)dealloc{
    [asset release];
    [super dealloc];
}

-(void)setMetadata{
    [GCAsset searchMetaDataForKey:@"CAT_USER_ID" andValue:[self userID] inBackgroundWithCompletion:^(GCResponse *response){
        if([response isSuccessful]){
            NSArray *assetArray = [response object];
            if([assetArray count] > 0){
                for(GCAsset *old in assetArray){
                    [old deleteMetaDataForKey:@"CAT_USER_ID"];
                }
            }
        }
        NSDictionary *objectData = [[[[self asset] verify] object] objectAtIndex:0];
        [self setAsset:[GCAsset objectWithDictionary:objectData]];
        [[self asset] setMetaData:[self userID] forKey:@"CAT_USER_ID" inBackgroundWithCompletion:^(BOOL successful){
            [self hideHUD];
            [[self navigationController] setNavigationBarHidden:NO];
            [[self navigationController] popViewControllerAnimated:YES];
        }];
    }];
}

-(void)objectTappedAtIndex:(NSInteger)index{
    [[self navigationController] setNavigationBarHidden:YES];
    [self hideHUD];
    [self showHUDWithTitle:@"Uploading Avatar" andOpacity:.7];
    [self setAsset:[objects objectAtIndex:index]];
    GCParcel *parcel = [GCParcel objectWithAssets:[NSArray arrayWithObject:[self asset]] andChutes:[NSArray arrayWithObject:[self chute]]];
    [parcel startUploadWithTarget:self andSelector:@selector(setMetadata)];
}

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

@end
