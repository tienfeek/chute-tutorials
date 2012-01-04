//
//  ViewController.m
//  ChuteAvatars
//
//  Created by Brandon Coston on 12/30/11.
//  Copyright (c) 2011 __MyCompanyName__. All rights reserved.
//

#import "ViewController.h"
#import "imagePicker.h"
#import "avatarView.h"

@implementation ViewController
@synthesize chute = _chute;

-(void)dealloc{
    [_chute release];
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

#pragma mark - View lifecycle

-(IBAction)chooseAvatarClicked:(id)sender{
//    if(![self chute])
//        return;
    if([[[userID text] stringByReplacingOccurrencesOfString:@" " withString:@""] length] == 0)
        return;
    imagePicker *picker = [[imagePicker alloc] init];
    [picker setChute:[self chute]];
    [picker setUserID:[userID text]];
    [picker setObjects:[[GCAccount sharedManager] assetsArray]];
    [[self navigationController] pushViewController:picker animated:YES];
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
